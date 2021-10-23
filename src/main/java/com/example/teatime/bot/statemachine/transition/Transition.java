package com.example.teatime.bot.statemachine.transition;

/**
 * Интерфейс для переходов - способов связать действие с сообщением пользователя
 */
public interface Transition {
  boolean match(String text);
}
