package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.teatime.bot.statemachine.MessageTools.getSendMessage;
import static com.example.teatime.bot.statemachine.MessageTools.setKeyboard;

@Component
public class TeaListPage implements Page {
  private static final String[][] keyboard = new String[][]{
      {KeyTransitions.BACK.getTitle()},
      {KeyTransitions.MAIN_PAGE.getTitle()},
      {KeyTransitions.CREATE_TEA.getTitle()}
  };

  @Override
  public SendMessage getPageMessage(Message receivedMessage) {
    SendMessage sendMessage = getSendMessage(receivedMessage);
    setKeyboard(keyboard, sendMessage);
    Long teaTypeId = MessageTools.getIdFromMessage(receivedMessage.getText());
    sendMessage.setText("Отображаем список чаев для типа чая " + teaTypeId + " : /tea_1, /tea_2");
    return sendMessage;
  }
}
