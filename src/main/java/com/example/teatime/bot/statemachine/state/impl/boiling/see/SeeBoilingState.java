package com.example.teatime.bot.statemachine.state.impl.boiling.see;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.BoilingElement;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.DeleteWithConfirmPage;
import com.example.teatime.bot.statemachine.page.impl.DeleteWithNameInputPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.insubd.EditBoilingPage;
import com.example.teatime.bot.statemachine.page.impl.boilingelement.insubd.CreateBoilingElementPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.delete.DeleteBoilingState;
import com.example.teatime.bot.statemachine.state.impl.boiling.insubd.CreateBoilingState;
import com.example.teatime.bot.statemachine.state.impl.boilingelement.delete.DeleteBoilingElementState;
import com.example.teatime.bot.statemachine.state.impl.boilingelement.insubd.CreateBoilingElementState;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.BoilingElementService;
import com.example.teatime.service.api.BoilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;
import java.util.Set;

import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.BOILING;
import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.BOILING_ELEMENT;

@Component
public class SeeBoilingState extends AbstractState {
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
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(Message message, StateMachine stateMachine) {
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
    getPageManager().sendPageMessage(CreateBoilingElementPage.class, message, stateMachine);
  }

  @Override
  public void catchIdEdit(Message message, StateMachine stateMachine) {
    Boiling boiling = boilingService.getBoilingById(LinkTransitions.getIdFromLink(message.getText()));
    stateMachine.getDataManager().setObject(BOILING, boiling);
    stateMachine.setState(CreateBoilingState.class);
    getPageManager().sendPageMessage(EditBoilingPage.class, message, stateMachine);
  }

  @Override
  public void catchIdDelete(Message message, StateMachine stateMachine) {
    Boiling boiling = boilingService.getBoilingById(LinkTransitions.getIdFromLink(message.getText()));
    stateMachine.getDataManager().setObject(BOILING, boiling);
    stateMachine.setState(DeleteBoilingState.class);
    getPageManager().sendPageMessage(DeleteWithNameInputPage.class, message, stateMachine);
  }

  @Override
  public void deleteLast(Message message, StateMachine stateMachine) {
    stateMachine.setState(DeleteBoilingElementState.class);
    getPageManager().sendPageMessage(DeleteWithConfirmPage.class, message, stateMachine);
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
