package com.example.teatime.bot.statemachine.transition;

public interface Transition {
  boolean match(String text);
}
