package com.example.teatime.bot.statemachine.state.impl.boiling.delete;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.DeleteSuccessPage;
import com.example.teatime.bot.statemachine.page.impl.DeleteUnsuccessfulPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.BoilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Set;

@Component
public class DeleteBoilingState extends AbstractState {
  private BoilingService boilingService;

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void unknownMessage(Message message, StateMachine machine) {
    Boiling boiling = machine.getDataManager().getObject(DataKeys.BOILING, Boiling.class);
    if (boiling.getTitle().equals(message.getText())) {
      boilingService.delete(boiling);
      getPageManager().sendPageMessage(DeleteSuccessPage.class, message, machine);
    } else {
      getPageManager().sendPageMessage(DeleteUnsuccessfulPage.class, message, machine);
    }
    mainPage(message, machine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(DataKeys.BOILING);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
