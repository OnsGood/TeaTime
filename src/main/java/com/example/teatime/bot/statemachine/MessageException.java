package com.example.teatime.bot.statemachine;

/**
 * Базовое исключение для ситуаций обработки сообщений пользователя
 */
public class MessageException extends RuntimeException {
  public MessageException(String message) {
    super(message);
  }

  public MessageException(String message, Throwable cause) {
    super(message, cause);
  }
}
