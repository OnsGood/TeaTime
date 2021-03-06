package com.example.teatime.bot.statemachine.pagemanager.api;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;

/**
 * Посредник для получения бинов страниц по их классам
 *
 * @apiNote разрешено использовать только там, где можно знать о реализациях страниц
 */
public interface PageManager {
  Page getPage(Class<? extends Page> pageClass);

  /**
   * Фасад для отправки сообщений от страниц. <br>
   * В этом методе мы:
   * <ul>
   *   <li>находим страницу по классу</li>
   *   <li>генерируем сообщение страницы</li>
   *   <li>отправляем сообщение пользователю</li>
   * </ul>
   *  @param pageClass класс сообщения
   *
   * @param receivedMessage сообщение, пришедшее пользователю
   * @param stateMachine    машина состояний из которой отправляем сообщение
   */
  void sendPageMessage(Class<? extends Page> pageClass, MessageDto receivedMessage, StateMachine stateMachine);

  /**
   * Фасад для отправки ответов на колбэк страниц
   */
  void sendCallbackResponse(MessageDto receivedMessage, StateMachine stateMachine);
}
