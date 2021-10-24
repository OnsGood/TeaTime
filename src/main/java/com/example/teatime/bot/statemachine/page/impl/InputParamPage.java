package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import static com.example.teatime.bot.statemachine.MessageTools.getSendMessage;

@Component
public class InputParamPage implements Page {

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = getSendMessage(receivedMessage);
    ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
    replyKeyboardRemove.setRemoveKeyboard(true);
    sendMessage.setReplyMarkup(replyKeyboardRemove);
    sendMessage.setText("Вводите:");
    return sendMessage;
  }
}
