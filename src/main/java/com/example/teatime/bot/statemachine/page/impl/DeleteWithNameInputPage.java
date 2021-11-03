package com.example.teatime.bot.statemachine.page.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class DeleteWithNameInputPage implements Page {

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    MessageTools.removeSpecialKeyboard(sendMessage);
    sendMessage.setText("Введите удаляемой сущности для подтверждения своих намерений:");
    return sendMessage;
  }
}