package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.MessageTools;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MainPage implements Page {
  @Override
  public SendMessage getPageMessage(Message receivedMessage) {
    SendMessage sendMessage = MessageTools.getSendMessage(receivedMessage);
    MessageTools.setKeyboard(new String[][]{{KeyTransitions.TEA_TYPE_LIST.getTitle()}, {KeyTransitions.TEA_NAME_SEARCH.getTitle()}}, sendMessage);
    sendMessage.setText("Отображаем главное меню");
    return sendMessage;
  }
}
