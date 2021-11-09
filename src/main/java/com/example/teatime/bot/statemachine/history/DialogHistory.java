package com.example.teatime.bot.statemachine.history;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.state.api.State;

/**
 * Хранит историю общения с пользователем.
 * Умеет перемещаться в прошлые состояния
 */
public final class DialogHistory {
  private static final Logger log = Logger.getLogger(DialogHistory.class);
  private final Deque<StateResponse> trackedHistory;
  private final LinkedList<StateResponse> untrackedHistory;
  private final int historyLength;

  public DialogHistory(int historyLength) {
    trackedHistory = new LinkedList<>();
    untrackedHistory = new LinkedList<>();
    this.historyLength = historyLength;
  }

  /**
   * Новая запись истории, которая помечена как отслеживаемая.  <br>
   * Доступно только для использования внутри пакета. <br>
   *
   * @param state   состояние бота
   * @param message новое пришедшее сообщение
   */
  void newHistory(State state, Message message) {
    StateResponse stateResponse = new StateResponse(state, message);
    if(!stateResponse.equals(trackedHistory.peekFirst())) {
      log.info("add new history " + stateResponse);
      trackedHistory.addFirst(stateResponse);
    } else {
      log.info("try to add duplicated history. rollback. " + stateResponse);
    }
    if (trackedHistory.size() > historyLength) {
      StateResponse deletedResponse = trackedHistory.removeLast();
      log.info("old history '" + deletedResponse + "' out of range. deleted");
    }
  }

  /**
   * Новая запись перехода между состояниями. <br>
   * Доступно только для использования внутри пакета. <br>
   *
   * @param state   состояние бота
   * @param message новое пришедшее сообщение
   */
  void newUntrackedHistory(State state, Message message) {
    StateResponse stateResponse = new StateResponse(state, message);
    untrackedHistory.addFirst(stateResponse);
    if (untrackedHistory.size() > 3) {
      untrackedHistory.removeLast();
    }
  }

  /**
   * Перевести бот в прошлое состояние
   *
   * @param stateMachine машина состояний
   */
  public void goToPrevState(StateMachine stateMachine) {
    StateResponse stateResponse = null;

    if (!trackedHistory.isEmpty()) {
      stateResponse = trackedHistory.removeFirst();
      if (!trackedHistory.isEmpty() && stateResponse.equals(untrackedHistory.get(1))) {
        stateResponse = trackedHistory.removeFirst();
      }
    }

    Optional.ofNullable(stateResponse)
      .ifPresent(s -> s.revert(stateMachine));
  }

  /**
   * Перевести бот в текущее состояние. <br>
   *  Другими словами он повторит прошлую команду пользователя, записанную в историю <br>
   *
   * @param stateMachine машина состояний
   */
  public void goToCurrentState(StateMachine stateMachine) {
    StateResponse stateResponse = null;

    if (!trackedHistory.isEmpty()) {
      stateResponse = trackedHistory.peekFirst();
    }

    Optional.ofNullable(stateResponse)
      .ifPresent(s -> s.revert(stateMachine));
  }

  public void eraseHistory() {
    trackedHistory.clear();
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

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      StateResponse that = (StateResponse) o;
      return Objects.equals(state, that.state) && Objects.equals(message.getText(), that.message.getText());
    }

    @Override
    public int hashCode() {
      return Objects.hash(state, message.getText());
    }
  }
}
