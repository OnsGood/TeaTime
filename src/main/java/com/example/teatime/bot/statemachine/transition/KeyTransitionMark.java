package com.example.teatime.bot.statemachine.transition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface KeyTransitionMark {
  KeyTransitions keyTransition();
}
