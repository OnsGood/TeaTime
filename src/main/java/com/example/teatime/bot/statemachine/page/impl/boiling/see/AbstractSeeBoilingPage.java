package com.example.teatime.bot.statemachine.page.impl.boiling.see;

import java.util.Iterator;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.BoilingElement;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.BoilingElementService;

import static com.example.teatime.bot.statemachine.MessageTools.*;

public abstract class AbstractSeeBoilingPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.CREATE_BOILING_ELEMENT.getTitle()},
    {KeyTransitions.DELETE_LAST_BOILING_ELEMENT.getTitle()},
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private final BoilingElementService boilingElementService;

  protected AbstractSeeBoilingPage(BoilingElementService boilingElementService) {
    this.boilingElementService = boilingElementService;
  }

  @Override
  public SendMessage getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    setKeyboard(keyboard, sendMessage);
    StringBuilder builder = new StringBuilder();

    Boiling boiling = getBoiling(receivedMessage, stateMachine);

    long boilingId = boiling.getId();

    builder
      .append("Способ заварки '").append(boiling.getTitle()).append("' ").append("для чая '").append(boiling.getTea().getTitle()).append("'").append("\n")
      .append("\n")
      .append(boiling.getDescription()).append("\n")
      .append("\n")
      .append("Редактировать - ").append(LinkTransitions.EDIT.makeLink(boilingId)).append("\n")
      .append("\n")
      .append("Удалить - ").append(LinkTransitions.DELETE.makeLink(boilingId)).append("\n")
      .append("\n")
      .append(getBoilingElements(boiling));


    sendMessage.setText(builder.toString());
    return sendMessage;
  }

  private String getBoilingElements(Boiling boiling) {
    Iterator<BoilingElement> boilingElements = boilingElementService.listElementsByBoilingSortedByNumber(boiling).iterator();

    StringBuilder stringBuilder = new StringBuilder()
      .append("\n");

    if (boilingElements.hasNext()) {
      stringBuilder
        .append("Заварки: ").append("\n");

      while (boilingElements.hasNext()) {
        BoilingElement boilingElement = boilingElements.next();
        stringBuilder
          .append(boilingElement.getNumber())
          .append(" | ")
          .append(boilingElement.getTemperature())
          .append("°c | ")
          .append(boilingElement.getSeconds())
          .append(" секунд | ")
          .append(boilingElement.getMass())
          .append(" грамм")
          .append("\n");
      }

    }

    return stringBuilder.toString();
  }

  protected abstract Boiling getBoiling(MessageDto receivedMessage, StateMachine stateMachine);


}
