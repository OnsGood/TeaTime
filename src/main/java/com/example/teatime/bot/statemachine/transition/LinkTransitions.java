package com.example.teatime.bot.statemachine.transition;

import com.example.teatime.bot.statemachine.MessageException;

/**
 * Переходы для внутренних ссылок
 * @apiNote внутренняя ссылка всегда соответствует форме: /someText_id
 */
public enum LinkTransitions implements Transition {
  GO("go", "Переход"),
  GO1("go1", "Еще один переход"),

  EDIT("edit", "Редактировать"),
  DELETE("delete", "Удалить");

  private static final String PREFIX_START = "/";
  private static final String PREFIX_END = "_";

  private final String linkText;
  private final String description;

  LinkTransitions(String linkText, String description) {
    this.linkText = linkText;
    this.description = description;
  }

  /**
   * Создать текст ссылки из id
   */
  public String makeLink(long id) {
    return getPrefix() + id;
  }

  /**
   * Создать префикс ссылки
   */
  public String getPrefix() {
    return PREFIX_START + linkText + PREFIX_END;
  }

  /**
   * Получить id из текста ссылки
   */
  public static Long getIdFromLink(String messageText) {
    String[] parts = messageText.split(PREFIX_END);
    if (parts.length == 2) {
      return Long.valueOf(parts[1]);
    } else {
      throw new MessageException("Wrong text format, id not found");
    }
  }

  @Override
  public boolean match(String text) {
    return text.matches(getPrefix() + ".*");
  }

}
