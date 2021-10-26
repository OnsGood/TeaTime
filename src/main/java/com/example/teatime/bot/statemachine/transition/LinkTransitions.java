package com.example.teatime.bot.statemachine.transition;

/**
 * Переходы для внутренних ссылок бота
 */
public enum LinkTransitions implements Transition {
  TEA_TYPE("/teaType_", "Вид чая"),
  TEA("/tea_", "Чай"),

  EDIT("/edit_", "Редактировать"),
  DELETE("/delete_", "Удалить");

  private final String prefix;
  private final String description;

  LinkTransitions(String prefix, String description) {
    this.prefix = prefix;
    this.description = description;
  }

  public String makeLink(long id) {
    return prefix + id;
  }

  public String getPrefix() {
    return prefix;
  }

  @Override
  public boolean match(String text) {
    return text.matches(prefix + ".*");
  }

}
