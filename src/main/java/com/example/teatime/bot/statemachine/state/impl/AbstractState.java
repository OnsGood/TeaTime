package com.example.teatime.bot.statemachine.state.impl;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.WrongStatePage;
import com.example.teatime.bot.statemachine.pagemanager.api.PageManager;
import com.example.teatime.bot.statemachine.state.api.State;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Базовое состояние, обычно бросает ошибки
 */
public abstract class AbstractState implements State {
  private static final Logger log = Logger.getLogger(AbstractState.class);
  private PageManager pageManager;

  @Autowired
  public final void setPageManager(PageManager pageManager) {
    this.pageManager = pageManager;
  }

  protected final PageManager getPageManager() {
    return pageManager;
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
  public void unknownMessage(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void listTeaTypes(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchIdGo(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchIdGo1(Message message, StateMachine stateMachine) {
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
  public void insupd(Message message, StateMachine stateMachine) {
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
  public void setTea(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setTime(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setTemp(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setMass(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void deleteLast(Message message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  private void sendNotAllowedCommandInStateErrorMessage(Message message, StateMachine stateMachine) {
    log.error("message can not resolved from abstract state. Message - " + message.getText());
    stateMachine.setState(stateMachine.getStateManager().getDefaultStateClass());
    getPageManager().sendPageMessage(WrongStatePage.class, message, stateMachine);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
