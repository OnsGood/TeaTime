package com.example.teatime.bot.statemachine.transition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для динамического определения Transition, по которому будет определен вызов метода
 * Только для класса State
 *
 * @author OnsGood
 * @see com.example.teatime.bot.statemachine.state.api.State
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyTransitionMark {
  KeyTransitions[] keyTransition();
}
