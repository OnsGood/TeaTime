package com.example.teatime.bot.statemachine;

public class MessageException extends RuntimeException {
  public MessageException(String message) {
    super(message);
  }

  public MessageException(String message, Throwable cause) {
    super(message, cause);
  }
}
