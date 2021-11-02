package com.example.teatime.bot.statemachine;

import com.example.teatime.bot.statemachine.datamanager.api.DataManager;
import com.example.teatime.bot.statemachine.state.api.State;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Машина состояний, которая принимает новое сообщение и делегирует его обработку одному из своих состояний.
 *
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
  void setPollingBot(TelegramLongPollingBot pollingBot);

  /**
   * Получить бот
   */
  TelegramLongPollingBot getPollingBot();

  /**
   * Обработать сообщение. <br>
   * Основной метод машины состояний. <br>
   * @param message сообщение для обработки
   */
  void resolveMessage(Message message);

  /**
   * Обработать предыдущую команду пользователя
   */
  void resolvePrevMessage();

  /**
   * Возвращает менеджер данных машины
   */
  DataManager getDataManager();
}
