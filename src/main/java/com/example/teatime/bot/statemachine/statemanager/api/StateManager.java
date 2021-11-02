package com.example.teatime.bot.statemachine.statemanager.api;

import com.example.teatime.bot.statemachine.state.api.State;

/**
 * Класс помощник для получения бинов состояний по их классам
 *
 * @apiNote разрешено использовать только там, где можно знать о реализациях состояний
 */
public interface StateManager {
  State getState(Class<? extends State> stateClass);

  Class<? extends State> getDefaultStateClass();
}
