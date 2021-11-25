package com.example.teatime.bot.statemachine.callback;

import com.example.teatime.bot.statemachine.MessageException;

/**
 * Специальное исключение для обработки колбэков
 */
public class CallbackException extends MessageException {
  public CallbackException(String message) {
    super(message);
  }

  public CallbackException(String message, Throwable cause) {
    super(message, cause);
  }
}
