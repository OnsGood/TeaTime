package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MainPage implements Page {

  @Override
  public SendMessage getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = MessageTools.makeSendMessage(receivedMessage);
    MessageTools.setKeyboard(new String[][]{{KeyTransitions.TEA_TYPE_LIST.getTitle()}, {KeyTransitions.TEA_NAME_SEARCH.getTitle()}}, sendMessage);
    sendMessage.setText("Вы находитесь в главном меню бота. Тыкайте на клавиши, и получайте что вам надо");
    return sendMessage;
  }
}
