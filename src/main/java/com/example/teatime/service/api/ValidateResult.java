package com.example.teatime.service.api;

/**
 * POJO для отображения результатов валидации с сообщением
 */
public record ValidateResult(String message, boolean allGood) {
  private static final ValidateResult GOOD = new ValidateResult("", true);

  public static ValidateResult getGood() {
    return GOOD;
  }

  public static ValidateResult getBad(String message) {
    return new ValidateResult(message, false);
  }

  public String getMessage() {
    return message;
  }

  public boolean isAllGood() {
    return allGood;
  }
}
