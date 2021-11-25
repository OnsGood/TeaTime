package com.example.teatime.bot.life;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.teatime.bot.statemachine.StateMachine;

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

    MessageDto messageDto = buildMessageDto(update);

    AsyncQueueRunner userMessageAsyncQueueRunner = Optional.ofNullable(stateMachineMap.get(messageDto.getUserId()))
            .orElseGet(() -> {
              log.info("new session has been started for user - " + messageDto.getUserId());
              StateMachine stateMachine = prototypeFactory.getObject();
              stateMachine.setBot(pollingBot);
              AsyncQueueRunner asyncQueueRunner = new AsyncQueueRunner(stateMachine);
              stateMachineMap.put(messageDto.getUserId(), asyncQueueRunner);
              return asyncQueueRunner;
            });

    log.info("preparing to resolve message for user - " + messageDto.getUserId());

    userMessageAsyncQueueRunner.addMessage(messageDto);
  }

  private MessageDto buildMessageDto(Update update) {
    MessageDto.Builder messageBuilder = new MessageDto.Builder();
    if (Objects.nonNull(update.getCallbackQuery())) {
      messageBuilder
        .setChatId(update.getCallbackQuery().getMessage().getChatId())
        .setText(update.getCallbackQuery().getData())
        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
        .setIsCallback(true)
        .setUserId(update.getCallbackQuery().getFrom().getId());
    } else if (Objects.nonNull(update.getMessage())) {
      messageBuilder
        .setChatId(update.getMessage().getChatId())
        .setText(update.getMessage().getText())
        .setMessageId(update.getMessage().getMessageId())
        .setIsCallback(false)
        .setUserId(update.getMessage().getFrom().getId());
    } else {
      throw new TelegramMessageResolvingException("Незнакомый тип сообщения");
    }
    return messageBuilder.build();
  }

  private static class AsyncQueueRunner {
    private final StateMachine stateMachine;
    private final ConcurrentLinkedQueue<MessageDto> consumerQueue;
    private volatile boolean isProcessQueue;

    public AsyncQueueRunner(StateMachine stateMachine) {
      this.stateMachine = stateMachine;
      consumerQueue = new ConcurrentLinkedQueue<>();
      isProcessQueue = false;
    }

    public void addMessage(MessageDto message) {
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
