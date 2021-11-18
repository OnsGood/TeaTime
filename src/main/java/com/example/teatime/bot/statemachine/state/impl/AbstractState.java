package com.example.teatime.bot.statemachine.state.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.WrongStatePage;
import com.example.teatime.bot.statemachine.pagemanager.api.PageManager;
import com.example.teatime.bot.statemachine.state.api.State;

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
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void back(MessageDto message, StateMachine stateMachine) {
    stateMachine.resolvePrevMessage();
  }

  @Override
  public void unknownMessage(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void listTeaTypes(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchIdGo(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchIdGo1(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchIdDelete(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void catchIdEdit(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void listTeaFromName(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void insupd(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setTitle(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setDescr(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setType(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setTea(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setTime(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setTemp(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void setMass(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  @Override
  public void deleteLast(MessageDto message, StateMachine stateMachine) {
    sendNotAllowedCommandInStateErrorMessage(message, stateMachine);
  }

  private void sendNotAllowedCommandInStateErrorMessage(MessageDto message, StateMachine stateMachine) {
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
