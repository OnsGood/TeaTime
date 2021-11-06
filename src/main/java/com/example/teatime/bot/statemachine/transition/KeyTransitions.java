package com.example.teatime.bot.statemachine.transition;

/**
 * Переходы для клавиш на клавиатуре
 */
public enum KeyTransitions implements Transition {
  MAIN_PAGE("Главное меню"),
  BACK("Назад"),

  TEA_NAME_SEARCH("Поиск чая по названию"),

  CREATE_TEA("Создать новый чай"),
  SAVE("Сохранить"),
  CREATE_TEA_TYPE("Создать новый вид чая"),
  CREATE_BOILING("Создать способ заваривания"),
  CREATE_BOILING_ELEMENT("Добавить заварку"),

  SET_TITLE("Ввести название"),
  SET_DESCR("Ввести описание"),
  SET_TYPE("Выбрать вид"),
  SET_TEA("Выбрать чай"),
  SET_TIME("Ввести время"),
  SET_TEMP("Ввести температуру"),
  SET_MASS("Ввести массу"),

  CHANGE_TITLE("Сменить название"),
  CHANGE_DESCR("Сменить описание"),
  CHANGE_TYPE("Сменить вид"),
  CHANGE_TEA("Сменить чай"),

  TEA_TYPE_LIST("Виды чая"),

  DELETE_LAST_BOILING_ELEMENT("Удалить последнюю заварку");

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
