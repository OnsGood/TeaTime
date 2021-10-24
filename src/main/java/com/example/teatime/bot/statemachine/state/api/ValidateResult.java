package com.example.teatime.bot.statemachine.state.api;

/**
 * POJO для отображения результатов валидации с сообщением
 */
public final class ValidateResult {
  private final String message;
  private final boolean allGood;

  private static final ValidateResult GOOD = new ValidateResult("", true);

  public static ValidateResult getGood() {
    return GOOD;
  }

  public static ValidateResult getBad(String message) {
    return new ValidateResult(message, false);
  }

  private ValidateResult(String message, boolean allGood) {
    this.message = message;
    this.allGood = allGood;
  }

  public String getMessage() {
    return message;
  }

  public boolean isAllGood() {
    return allGood;
  }
}
