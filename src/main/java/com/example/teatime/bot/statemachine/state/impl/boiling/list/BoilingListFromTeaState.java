package com.example.teatime.bot.statemachine.state.impl.boiling.list;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.insubd.CreateBoilingPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.see.SeeBoilingFromIdPage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.insubd.CreateBoilingState;
import com.example.teatime.bot.statemachine.state.impl.boiling.see.SeeBoilingState;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.BoilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;
import java.util.Set;

import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.TEA;

@Component("BoilingListFromTeaState")
public class BoilingListFromTeaState extends AbstractState implements State {
  private BoilingService boilingService;

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Override
  @Historical
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void insupd(Message message, StateMachine stateMachine) {
    Boiling boiling = new Boiling();
    stateMachine.getDataManager().setObject(DataKeys.BOILING, boiling);
    if (Objects.isNull(boiling.getTea())) {
      Tea parentTea = stateMachine.getDataManager().getObject(TEA, Tea.class);
      boiling.setTea(parentTea);
    }
    stateMachine.setState(CreateBoilingState.class);
    getPageManager().sendPageMessage(CreateBoilingPage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void catchIdGo(Message message, StateMachine stateMachine) {
    Boiling boiling = boilingService.getBoilingById(LinkTransitions.getIdFromLink(message.getText()));
    stateMachine.getDataManager().setObject(DataKeys.BOILING, boiling);
    stateMachine.setState(SeeBoilingState.class);
    getPageManager().sendPageMessage(SeeBoilingFromIdPage.class, message, stateMachine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(DataKeys.TEA);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
