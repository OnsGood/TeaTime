package com.example.teatime.bot.statemachine.statemanager.impl;

import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.statemanager.api.StateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;

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
  public State getDefaultState() {
    return Arrays.stream(states)
            .filter(s -> s.getClass().equals(MainPageState.class))
            .findAny()
            .orElseThrow(() -> new RuntimeException("State not found: " + MainPageState.class.getSimpleName()));
  }
}
