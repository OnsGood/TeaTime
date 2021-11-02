package com.example.teatime.bot.statemachine;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.state.api.State;

/**
 * Хранит историю общения с пользователем.
 * Умеет перемещаться в прошлые состояния
 */
public final class DialogHistory {
  private static final Logger log = Logger.getLogger(DialogHistory.class);
  private final Deque<StateResponse> stateResponses;
  private final int historyLength;

  public DialogHistory(int historyLength) {
    stateResponses = new LinkedList<>();
    this.historyLength = historyLength;
  }

  /**
   * Новая запись истории
   *
   * @param state   состояние бота
   * @param message новое пришедшее сообщение
   */
  public void newHistory(State state, Message message) {
    stateResponses.addFirst(new StateResponse(state, message));
    if (stateResponses.size() > historyLength) {
      StateResponse deletedResponse = stateResponses.removeLast();
      log.info("old history '" + deletedResponse + "' out of range. deleted");
    }
  }

  /**
   * Перевести бот в прошлое состояние
   *
   * @param stateMachine    машина состояний
   * @param historyPosition позиция записи на которую перейти
   */
  public void goTo(StateMachine stateMachine, int historyPosition) {
    StateResponse stateResponse = null;
    for (int i = 0; i < historyPosition; i++) {
      if (!stateResponses.isEmpty()) {
        stateResponse = stateResponses.removeFirst();
      }
    }
    Optional.ofNullable(stateResponse)
      .ifPresent(s -> s.revert(stateMachine));
  }

  /**
   * Элемент истории. Умеет переводить бот в свое состояние
   */
  private record StateResponse(State state, Message message) {
    void revert(StateMachine stateMachine) {
      log.info("reverting bot to state '" + state + "' with message '" + message.getText() + "'");
      stateMachine.setState(state.getClass());
      stateMachine.resolveMessage(message);
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", StateResponse.class.getSimpleName() + "[", "]")
        .add("state=" + state)
        .add("message=" + message.getText())
        .toString();
    }
  }
}
