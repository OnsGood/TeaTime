package com.example.teatime.bot.statemachine.page.impl.boiling.see;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.BoilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.teatime.bot.statemachine.MessageTools.makeSendMessage;
import static com.example.teatime.bot.statemachine.MessageTools.setKeyboard;

@Component
public class SeeBoilingPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private BoilingService boilingService;

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    setKeyboard(keyboard, sendMessage);
    StringBuilder builder = new StringBuilder();
    long boilingId = LinkTransitions.getIdFromLink(receivedMessage.getText());

    Boiling boiling = boilingService.getBoilingById(boilingId);

    builder
      .append("Способ заварки '").append(boiling.getTitle()).append("' ").append("для чая '").append(boiling.getTea().getTitle()).append("'").append("\n")
      .append("\n")
      .append(boiling.getDescription()).append("\n")
      .append("\n")
      .append("Перейти к заваркам - ").append(LinkTransitions.GO.makeLink(boilingId)).append("\n")
      .append("\n")
      .append("Редактировать - ").append(LinkTransitions.EDIT.makeLink(boilingId)).append("\n")
      .append("\n")
      .append("Удалить - ").append(LinkTransitions.DELETE.makeLink(boilingId)).append("\n");


    sendMessage.setText(builder.toString());
    return sendMessage;
  }
}
