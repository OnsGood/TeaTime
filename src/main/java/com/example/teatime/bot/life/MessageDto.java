package com.example.teatime.bot.life;

/**
 * Данные с внешнего сообщения пользователя
 */
public record MessageDto(Long userId, String text, Long chatId, Integer messageId, boolean isCallback) {

  public static final class Builder {
    private Long userId;
    private String text;
    private Long chatId;
    private boolean isCallback;
    private Integer messageId;

    public Builder() {
    }

    public Builder setUserId(Long userId) {
      this.userId = userId;
      return this;
    }

    public Builder setText(String text) {
      this.text = text;
      return this;
    }

    public Builder setChatId(Long chatId) {
      this.chatId = chatId;
      return this;
    }

    public Builder setIsCallback(boolean callback) {
      isCallback = callback;
      return this;
    }

    public Builder setMessageId(Integer messageId) {
      this.messageId = messageId;
      return this;
    }

    public MessageDto build() {
      return new MessageDto(userId, text, chatId, messageId, isCallback);
    }
  }
}
