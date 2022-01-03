package com.example.teatime.bot.statemachine.page.impl.boiling.see;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.BoilingElement;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Header;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.SeeFacade;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Text;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.service.api.BoilingElementService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Iterator;
import java.util.List;

public abstract class AbstractSeeBoilingPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private static final String[][] moderKeyboard = new String[][]{
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
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    Boiling boiling = getBoiling(receivedMessage, stateMachine);
    long boilingId = boiling.getId();

    return new PageMessageBuilder(receivedMessage)
      .keyboard(stateMachine.isUserModerator() ? moderKeyboard : keyboard)
      .part(new SeeFacade()
        .setHeader(
          new Header("Способ заварки '", boiling.getTitle(), "' для чая '", boiling.getTea().getTitle(), "'")
        )
        .setDescription(new Text(boiling.getDescription()))
        .setId(boilingId)
        .setShowEditAndDelete(stateMachine.isUserModerator())
      )
      .part(new Text(getBoilingElements(boiling)))
      .buildMessageList();
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
