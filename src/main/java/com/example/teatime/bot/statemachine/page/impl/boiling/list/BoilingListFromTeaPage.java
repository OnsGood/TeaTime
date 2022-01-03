package com.example.teatime.bot.statemachine.page.impl.boiling.list;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.GoListFacade;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.BoilingService;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class BoilingListFromTeaPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private static final String[][] moderKeyboard = new String[][]{
    {KeyTransitions.CREATE_BOILING.getTitle()},
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private TeaService teaService;
  private BoilingService boilingService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    Long teaId = LinkTransitions.getIdFromLink(receivedMessage.text());
    Iterable<Boiling> boilings = boilingService.listBoilingByTea(teaService.getTeaById(teaId));

    return new PageMessageBuilder(receivedMessage)
      .keyboard(stateMachine.isUserModerator() ? moderKeyboard : keyboard)
      .part(new GoListFacade<Boiling>()
        .setHead("Способы заварки:")
        .setList(boilings)
        .setToIdFunction(Boiling::getId)
        .setToTextFunction(Boiling::getTitle)
        .setNoFoundMessage("Способы заварки для этого чая не найдены. Создайте их.")
      )
      .buildMessageList();
  }
}
