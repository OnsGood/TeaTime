package com.example.teatime.bot.statemachine.state.impl;

import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.WrongStatePage;
import com.example.teatime.bot.statemachine.pagemanager.api.PageManager;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.statemanager.api.StateManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public abstract class AbstractState implements State {
  private static final Logger log = Logger.getLogger(AbstractState.class);

  private StateManager stateManager;
  private PageManager pageManager;

  @Autowired
  public void setPageManager(PageManager pageManager) {
    this.pageManager = pageManager;
  }

  protected final PageManager getPageManager() {
    return pageManager;
  }

  @Autowired
  public void setStateManager(StateManager stateManager) {
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
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
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
  public void listTeaFromTeaType(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  private void sendNotAllowedCommandInStateErrorMessage(Message message, StateMachine stateMachine) {
    log.error("Message can not resolved from abstract state. Message - " + message.getText());
    stateMachine.setState(stateManager.getDefaultState());
    MessageTools.sendMessage(getPageManager().getPage(WrongStatePage.class).getPageMessage(message), stateMachine.getPollingBot());
  }

  protected final void logState(Message message, Class<? extends State> stateClass) {
    log.info("main page showed from " + stateClass.getSimpleName() + ". Message - " + message.getText());
  }


  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
