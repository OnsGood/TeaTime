package com.example.teatime.bot.life;

/**
 * Данные с внешнего сообщения пользователя
 */
public record MessageDto(Long userId, String text, Long chatId) {

  public Long getUserId() {
    return userId;
  }

  public String getText() {
    return text;
  }

  public Long getChatId() {
    return chatId;
  }

  public static final class Builder {
    private Long userId;
    private String text;
    private Long chatId;

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

    public MessageDto build() {
      return new MessageDto(userId, text, chatId);
    }
  }
}
