package com.example.teatime.bot.statemachine.page.impl.boiling.see;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.BoilingElementService;
import com.example.teatime.service.api.BoilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeeBoilingFromIdPage extends AbstractSeeBoilingPage {
  private BoilingService boilingService;

  @Autowired
  public SeeBoilingFromIdPage(BoilingElementService boilingElementService) {
    super(boilingElementService);
  }

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Override
  protected Boiling getBoiling(MessageDto receivedMessage, StateMachine stateMachine) {
    long boilingId = LinkTransitions.getIdFromLink(receivedMessage.getText());
    return boilingService.getBoilingById(boilingId);
  }
}
