package com.example.teatime.bot.statemachine.state.impl;

import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.WrongStatePage;
import com.example.teatime.bot.statemachine.pagemanager.api.PageManager;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.statemanager.api.StateManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Базовое состояние, обычно бросает ошибки
 */
@Component
public abstract class AbstractState implements State {
  private static final Logger log = Logger.getLogger(AbstractState.class);

  private StateManager stateManager;
  private PageManager pageManager;

  @Autowired
  public final void setPageManager(PageManager pageManager) {
    this.pageManager = pageManager;
  }

  protected final PageManager getPageManager() {
    return pageManager;
  }

  @Autowired
  public final void setStateManager(StateManager stateManager) {
    this.stateManager = stateManager;
  }

  protected final StateManager getStateManager() {
    return stateManager;
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void back(Message message, StateMachine stateMachine) {
    stateMachine.resolvePrevMessage();
  }

  @Override
  public void unknownMessage(Message message, StateMachine machine) {
    sendNotAllowedCommandInStateErrorMessage(message, machine);
  }

  @Override
  public void listTeaTypes(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchTeaTypeId(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchTeaId(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchIdDelete(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchIdEdit(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void listTeaFromName(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void insupdTea(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setTitle(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setDescr(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setType(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void createTeaType(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  private void sendNotAllowedCommandInStateErrorMessage(Message message, StateMachine stateMachine) {
    log.error("Message can not resolved from abstract state. Message - " + message.getText());
    stateMachine.setState(stateManager.getDefaultState());
    MessageTools.sendMessage(getPageManager().getPage(WrongStatePage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  protected final void logState(Message message, Class<? extends State> stateClass) {
    log.info("page showed from " + stateClass.getSimpleName() + ". Message - " + message.getText());
  }


  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
