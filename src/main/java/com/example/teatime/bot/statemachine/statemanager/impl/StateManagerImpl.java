package com.example.teatime.bot.statemachine.statemanager.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.statemanager.api.StateManager;

@Component
public class StateManagerImpl implements StateManager {
  private State[] states;

  @Autowired
  public void setStates(State[] states) {
    this.states = states;
  }

  @Override
  public State getState(Class<? extends State> stateClass) {
    Assert.notNull(stateClass, "stateClass must be not null");
    return Arrays.stream(states)
      .filter(s -> s.getClass().equals(stateClass))
      .findAny()
      .orElseThrow(() -> new RuntimeException("State not found: " + stateClass.getSimpleName()));
  }

  @Override
  public Class<? extends State> getDefaultStateClass() {
    return MainPageState.class;
  }
}
