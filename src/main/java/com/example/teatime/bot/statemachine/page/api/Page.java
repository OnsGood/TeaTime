package com.example.teatime.bot.statemachine.page.api;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.teatime.bot.life.MessageDto;
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

}
