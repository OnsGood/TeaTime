package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.teatime.bot.statemachine.MessageTools.makeSendMessage;

@Component
public class DeleteUnsuccessfulPage implements Page {

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    sendMessage.setText("Ошибка удаления!");
    return sendMessage;
  }
}