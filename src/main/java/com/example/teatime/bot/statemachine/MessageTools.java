package com.example.teatime.bot.statemachine;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Утилитный класс для шаблонных действий с сообщениями
 */
public final class MessageTools {
  private static final Logger log = Logger.getLogger(MessageTools.class);

  private MessageTools() {

  }

  public static Long getIdFromMessage(String messageText) {
    String[] parts = messageText.split("_");
    if (parts.length == 2) {
      return Long.valueOf(parts[1]);
    } else {
      throw new MessageException("wrong text format, id not found");
    }
  }

  public static SendMessage getSendMessage(Message message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(message.getChatId().toString());
    return sendMessage;
  }

  public static void setKeyboard(String[][] keys, SendMessage sendMessage) {
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    List<KeyboardRow> keyboard = new ArrayList<>();
    for (String[] keyRows : keys) {
      KeyboardRow row = new KeyboardRow();
      row.addAll(Arrays.asList(keyRows));
      keyboard.add(row);
    }
    keyboardMarkup.setKeyboard(keyboard);
    sendMessage.setReplyMarkup(keyboardMarkup);
  }

  public static void sendMessage(SendMessage sendMessage, TelegramLongPollingBot botToSend) {
    try {
      botToSend.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(e.getMessage(), e);
    }
  }
}
