package com.example.teatime.bot.statemachine.page.impl.boiling.see;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.service.api.BoilingElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SeeBoilingFromDataManagerPage extends AbstractSeeBoilingPage {
  @Autowired
  public SeeBoilingFromDataManagerPage(BoilingElementService boilingElementService) {
    super(boilingElementService);
  }

  @Override
  protected Boiling getBoiling(Message receivedMessage, StateMachine stateMachine) {
    return stateMachine.getDataManager().getObject(DataKeys.BOILING, Boiling.class);
  }
}
