package com.example.teatime.bot.statemachine;

import com.example.teatime.bot.statemachine.state.api.State;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Машина состояний, которая принимает новое сообщение и делегериует его обработку одному из своих состояний.
 *
 */
public interface StateMachine {
  /**
   * Установить состояние
   */
  void setState(State state);

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
   * Обработать сообщение
   * Основной метод машины состояний.
   * @param message сообщение для обработки
   */
  void resolveMessage(Message message);
}
