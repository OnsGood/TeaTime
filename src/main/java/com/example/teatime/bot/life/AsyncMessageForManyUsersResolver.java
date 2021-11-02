package com.example.teatime.bot.life;

import com.example.teatime.bot.statemachine.StateMachine;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class AsyncMessageForManyUsersResolver implements MessageResolver {
  private static final Logger log = Logger.getLogger(AsyncMessageForManyUsersResolver.class);

  private final Map<Long, AsyncQueueRunner> stateMachineMap;

  @Autowired
  private ObjectFactory<StateMachine> prototypeFactory;

  public AsyncMessageForManyUsersResolver() {
    stateMachineMap = new HashMap<>();
  }

  @Override
  public void resolveUpdate(Update update, TelegramLongPollingBot pollingBot) {
    log.info("new message prepared to resolving");

    Long userId = Optional.ofNullable(update.getMessage())
            .map(Message::getFrom)
            .map(User::getId)
            .orElseThrow(() -> new TelegramMessageResolvingException("Не найден владелец сообщения"));

    AsyncQueueRunner userMessageAsyncQueueRunner = Optional.ofNullable(stateMachineMap.get(userId))
            .orElseGet(() -> {
              log.info("new session has been started for user - " + userId);
              StateMachine stateMachine = prototypeFactory.getObject();
              stateMachine.setBot(pollingBot);
              AsyncQueueRunner asyncQueueRunner = new AsyncQueueRunner(stateMachine);
              stateMachineMap.put(userId, asyncQueueRunner);
              return asyncQueueRunner;
            });

    log.info("preparing to resolve message for user - " + userId);

    userMessageAsyncQueueRunner.addMessage(update.getMessage());
  }

  private static class AsyncQueueRunner {
    private final StateMachine stateMachine;
    private final ConcurrentLinkedQueue<Message> consumerQueue;
    private volatile boolean isProcessQueue;

    public AsyncQueueRunner(StateMachine stateMachine) {
      this.stateMachine = stateMachine;
      consumerQueue = new ConcurrentLinkedQueue<>();
      isProcessQueue = false;
    }

    public void addMessage(Message message) {
      consumerQueue.add(message);
      run();
    }

    private void run() {
      if (!consumerQueue.isEmpty() && !isProcessQueue) {
        isProcessQueue = true;
        CompletableFuture.runAsync(() -> {
          while (!consumerQueue.isEmpty()) {
            stateMachine.resolveMessage(consumerQueue.poll());
          }
          isProcessQueue = false;
        });
      }
    }

  }

}
