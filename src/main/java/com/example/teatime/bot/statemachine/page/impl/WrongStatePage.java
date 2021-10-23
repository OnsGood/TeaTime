package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class WrongStatePage implements Page {
  @Override
  public SendMessage getPageMessage(Message receivedMessage) {
    SendMessage sendMessage = getSendMessage(receivedMessage);
    setKeyboard(new String[][]{{KeyTransitions.TEA_TYPE_LIST.getTitle()}}, sendMessage);
    sendMessage.setText("В текущем состоянии диалога выполнение этой команды недоступно");
    return sendMessage;
  }
}
