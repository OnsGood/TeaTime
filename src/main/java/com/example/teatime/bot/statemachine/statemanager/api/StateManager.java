package com.example.teatime.bot.statemachine.statemanager.api;

import com.example.teatime.bot.statemachine.state.api.State;

public interface StateManager {
  State getState(Class<? extends State> stateClass);

  State getDefaultState();
}
