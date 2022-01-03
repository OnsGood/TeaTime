package com.example.teatime.bot.statemachine.state.impl.boiling.see;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.BoilingElement;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.DeleteWithConfirmPage;
import com.example.teatime.bot.statemachine.page.impl.DeleteWithNameInputPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.delete.DeleteBoilingHasCichlidsPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.insubd.EditBoilingPage;
import com.example.teatime.bot.statemachine.page.impl.boilingelement.insubd.CreateBoilingElementPage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.delete.DeleteBoilingState;
import com.example.teatime.bot.statemachine.state.impl.boiling.insubd.CreateBoilingState;
import com.example.teatime.bot.statemachine.state.impl.boilingelement.delete.DeleteBoilingElementState;
import com.example.teatime.bot.statemachine.state.impl.boilingelement.insubd.CreateBoilingElementState;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.BoilingElementService;
import com.example.teatime.service.api.BoilingService;

import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.*;

@Component("SeeBoilingState")
public class SeeBoilingState extends AbstractState implements State {
  private BoilingService boilingService;
  private BoilingElementService boilingElementService;

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Autowired
  public void setBoilingElementService(BoilingElementService boilingElementService) {
    this.boilingElementService = boilingElementService;
  }

  @Override
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(MessageDto message, StateMachine stateMachine) {
    Boiling boiling = stateMachine.getDataManager().getObject(BOILING, Boiling.class);
    BoilingElement boilingElement = new BoilingElement();
    boilingElement.setBoiling(boiling);
    Optional<BoilingElement> lastBoilingElement = boilingElementService.findElementMaxNumberByBoiling(boiling);

    long number = lastBoilingElement
      .map(BoilingElement::getNumber)
      .map(lastElemNum -> lastElemNum = lastElemNum + 1)
      .orElse(1L);
    boilingElement.setNumber(number);
    lastBoilingElement.ifPresent(lbe -> boilingElement.setMass(lbe.getMass()));

    stateMachine.getDataManager().setObject(BOILING_ELEMENT, boilingElement);
    stateMachine.setState(CreateBoilingElementState.class);
    sendPageMessage(CreateBoilingElementPage.class, message, stateMachine);
  }

  @Override
  public void catchIdEdit(MessageDto message, StateMachine stateMachine) {
    Boiling boiling = boilingService.getBoilingById(LinkTransitions.getIdFromLink(message.text()));
    stateMachine.getDataManager().setObject(BOILING, boiling);
    stateMachine.setState(CreateBoilingState.class);
    sendPageMessage(EditBoilingPage.class, message, stateMachine);
  }

  @Override
  public void catchIdDelete(MessageDto message, StateMachine stateMachine) {
    Boiling boiling = boilingService.getBoilingById(LinkTransitions.getIdFromLink(message.text()));
    if (boilingService.isAllowedToDelete(boiling)) {
      stateMachine.getDataManager().setObject(BOILING, boiling);
      stateMachine.setState(DeleteBoilingState.class);
      sendPageMessage(DeleteWithNameInputPage.class, message, stateMachine);
    } else {
      sendPageMessage(DeleteBoilingHasCichlidsPage.class, message, stateMachine);
      stateMachine.getDialogHistory().goToCurrentState(stateMachine);
    }

  }

  @Override
  public void deleteLast(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(DeleteBoilingElementState.class);
    sendPageMessage(DeleteWithConfirmPage.class, message, stateMachine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(BOILING, BOILING_ELEMENT);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
