package com.example.teatime.bot.statemachine.page.api;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.callback.CallbackException;
import com.example.teatime.bot.statemachine.StateMachine;

/**
 * Отвечает за отрисовку страниц.
 */
public interface Page {

  /**
   * @param receivedMessage сообщение, которое пришло боту
   * @param stateMachine    машина состояний, которая ответственна за обработку сообщения
   * @return список объектов sendMessage, которые будут отправлены по очереди. Сделано для двух клавиатур телеги, телега блин какого фига
   */
  List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine);

  /**
   * Отвечает на колбэк для этой страницы
   */
  default EditMessageText getCallbackResponse(MessageDto callbackMessage, StateMachine stateMachine) {
    throw new CallbackException("Обработка сообщений обратного вызова не применима к этой странице");
  }

}
