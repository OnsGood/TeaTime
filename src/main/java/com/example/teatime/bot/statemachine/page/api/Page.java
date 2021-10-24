package com.example.teatime.bot.statemachine.page.api;

import com.example.teatime.bot.statemachine.StateMachine;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Отвечает за отрисовку страниц.
 */
public interface Page {

  /**
   * @param receivedMessage сообщение, которое пришло боту
   * @param stateMachine    машина состояний, которая ответственна за обработку сообщения
   * @return сообщение, которое нужно отправить
   */
  SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine);

}
