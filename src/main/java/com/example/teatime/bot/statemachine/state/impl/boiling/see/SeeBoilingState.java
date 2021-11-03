package com.example.teatime.bot.statemachine.state.impl.boiling.see;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.DeleteWithNameInputPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.insubd.EditBoilingPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.delete.DeleteBoilingState;
import com.example.teatime.bot.statemachine.state.impl.boiling.insubd.CreateBoilingState;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.BoilingService;

@Component
public class SeeBoilingState extends AbstractState {
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
  public void catchIdGo(Message message, StateMachine stateMachine) {
    // не реализовано
  }

  @Override
  public void catchIdEdit(Message message, StateMachine stateMachine) {
    Boiling boiling = boilingService.getBoilingById(LinkTransitions.getIdFromLink(message.getText()));
    stateMachine.getDataManager().setObject(DataKeys.BOILING, boiling);
    stateMachine.setState(CreateBoilingState.class);
    getPageManager().sendPageMessage(EditBoilingPage.class, message, stateMachine);
  }

  @Override
  public void catchIdDelete(Message message, StateMachine stateMachine) {
    Boiling boiling = boilingService.getBoilingById(LinkTransitions.getIdFromLink(message.getText()));
    stateMachine.getDataManager().setObject(DataKeys.BOILING, boiling);
    stateMachine.setState(DeleteBoilingState.class);
    getPageManager().sendPageMessage(DeleteWithNameInputPage.class, message, stateMachine);
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
