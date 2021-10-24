package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MainPage implements Page {
  private static final Logger logger = LogManager.getLogger(MainPage.class);

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = MessageTools.getSendMessage(receivedMessage);
    MessageTools.setKeyboard(new String[][]{{KeyTransitions.TEA_TYPE_LIST.getTitle()}, {KeyTransitions.TEA_NAME_SEARCH.getTitle()}}, sendMessage);
    sendMessage.setText("Вы находитесь в главном меню бота. Тыкайте на клавиши, и получайте что вам надо");
    return sendMessage;
  }
}
