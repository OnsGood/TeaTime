package com.example.teatime.bot.statemachine.statemanager.impl;

import com.example.teatime.bot.statemachine.MessageException;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.statemanager.api.StateManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class StateManagerImpl implements StateManager {

  private ApplicationContext appContext;

  @Autowired
  public void setAppContext(ApplicationContext appContext) {
    this.appContext = appContext;
  }

  @Override
  public State getState(Class<? extends State> stateClass) {
    Assert.notNull(stateClass, "stateClass must be not null");
    try {
      return (State) appContext.getBean(stateClass.getSimpleName());
    } catch (BeansException e) {
      throw new MessageException("State not found: " + stateClass.getSimpleName(), e);
    }
  }

  @Override
  public Class<? extends State> getDefaultStateClass() {
    return MainPageState.class;
  }
}
