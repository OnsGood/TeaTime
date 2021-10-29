package com.example.teatime.bot.statemachine.transition;

/**
 * Интерфейс для переходов - способов связать действие бота с сообщением пользователя
 */
public interface Transition {
  /**
   * Соответствует ли текст условию перехода
   */
  boolean match(String text);
}
