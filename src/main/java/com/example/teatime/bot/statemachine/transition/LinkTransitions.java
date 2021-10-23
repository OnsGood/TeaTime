package com.example.teatime.bot.statemachine.transition;

/**
 * Переходы для внутренних ссылок бота
 */
public enum LinkTransitions implements Transition {
  GET_TEA_TYPE("/teaType_", "Вид чая"),
  EDIT_TEA_TYPE("/editTeaType_", "Редактировать вид чая"),
  GET_TEA("/tea_", "Чай"),
  EIT_TEA("/editTea_", "Редактировать чай");

  private final String prefix;
  private final String description;

  LinkTransitions(String prefix, String description) {
    this.prefix = prefix;
    this.description = description;
  }

  public String getPrefix() {
    return prefix;
  }

  @Override
  public boolean match(String text) {
    return text.matches(prefix + ".*");
  }

}
