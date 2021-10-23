package com.example.teatime.bot.life;

/**
 * Ошибка для слоя обработки сообщений, прочитанных ботом
 */
public class TelegramMessageResolvingException extends RuntimeException {
  public TelegramMessageResolvingException(String message) {
    super(message);
  }

  public TelegramMessageResolvingException(String message, Throwable cause) {
    super(message, cause);
  }
}
