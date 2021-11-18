package com.example.teatime.bot.statemachine.page.impl.boiling.insubd;

import java.util.List;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.teatime.bot.statemachine.MessageTools.makeSendMessage;
import static com.example.teatime.bot.statemachine.MessageTools.setKeyboard;

@Component
public class EditBoilingPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.SAVE.getTitle()},
    {KeyTransitions.CHANGE_TITLE.getTitle()},
    {KeyTransitions.CHANGE_DESCR.getTitle()},
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    setKeyboard(keyboard, sendMessage);
    StringBuilder builder = new StringBuilder();
    Boiling boiling = stateMachine.getDataManager().getObject(DataKeys.BOILING, Boiling.class);

    builder
      .append("Редактирование способа заварки - '")
      .append("\n")
      .append(boiling.getTitle()).append("' ").append("для чая '").append(boiling.getTea().getTitle()).append("'").append("\n")
      .append("\n")
      .append(boiling.getDescription()).append("\n")
      .append("\n");

    sendMessage.setText(builder.toString());
    return List.of(sendMessage);
  }
}
