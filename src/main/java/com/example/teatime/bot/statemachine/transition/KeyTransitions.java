package com.example.teatime.bot.statemachine.transition;

/**
 * Переходы для клавиш на клавиатуре
 */
public enum KeyTransitions implements Transition {
  MAIN_PAGE("Главное меню"),
  BACK("Назад"),
  TEA_NAME_SEARCH("Поиск чая по названию"),
  CREATE_TEA("Создать новый чай"),
  CREATE_TEA_TYPE("Создать новый вид чая"),
  TEA_TYPE_LIST("Виды чая");

  private final String title;

  KeyTransitions(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public String toString() {
    return title;
  }

  @Override
  public boolean match(String text) {
    return title.equals(text);
  }
}
