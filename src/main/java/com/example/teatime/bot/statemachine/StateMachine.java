package com.example.teatime.bot.statemachine;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.datamanager.api.DataManager;
import com.example.teatime.bot.statemachine.history.DialogHistory;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.statemanager.api.StateManager;

/**
 * Машина состояний, которая принимает новое сообщение и делегирует его обработку одному из своих состояний.
 */
public interface StateMachine {
  /**
   * Установить состояние
   */
  void setState(Class<? extends State> stateClass);

  /**
   * Получить состояние, которое установлено в машине сейчас
   */
  State getState();

  /**
   * Установить бот
   */
  void setBot(TelegramLongPollingBot pollingBot);

  /**
   * Получить бот
   */
  TelegramLongPollingBot getBot();

  /**
   * Обработать сообщение. <br>
   * Основной метод машины состояний. <br>
   *
   * @param message сообщение для обработки
   */
  void resolveMessage(MessageDto message);

  /**
   * Обработать предыдущую команду пользователя
   */
  void resolvePrevMessage();

  /**
   * Вернуть историю машины
   */
  DialogHistory getDialogHistory();

  /**
   * Возвращает менеджер данных машины
   */
  DataManager getDataManager();

  /**
   * Возвращает менеджер состояний машины
   */
  StateManager getStateManager();

  /**
   * Устанавливает роль пользователя машины.
   * @param isModerator
   */
  void setUserRule(boolean isModerator);

  /**
   * Возвращает тру, если пользователь модератор
   */
  boolean isUserModerator();
}
