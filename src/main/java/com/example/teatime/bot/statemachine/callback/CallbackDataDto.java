package com.example.teatime.bot.statemachine.callback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.google.common.base.Preconditions;

/**
 * Объект с данными колбэков
 */
public class CallbackDataDto {
  private final String originalBeanName;
  private final String data;

  private CallbackDataDto(String callbackText) {
    Preconditions.checkNotNull(callbackText, "callbackText is empty!");

    String[] datas = callbackText.split(";");

    if (datas.length != 2) {
      throw new CallbackException("ccallbackText has wrong format");
    }

    originalBeanName = datas[0];
    data = datas[1];
  }

  public static CallbackDataDto callbackDataToDto(String callbackText) {
    return new CallbackDataDto(callbackText);
  }

  public InlineKeyboardButton getButton(String buttonName) {
    InlineKeyboardButton button = new InlineKeyboardButton(buttonName);
    button.setCallbackData(toCallbackData());
    return button;
  }

  private String toCallbackData() {
    return getOriginalBeanName() + ";" + getData();
  }


  public String getOriginalBeanName() {
    return originalBeanName;
  }

  public String getData() {
    return data;
  }
}
