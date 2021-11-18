package com.example.teatime.bot.statemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.teatime.bot.life.MessageDto;

/**
 * Утилитный класс для шаблонных действий с сообщениями
 */
public final class MessageTools {
  private static final Logger log = Logger.getLogger(MessageTools.class);

  private MessageTools() {
    throw new IllegalStateException("Not allowed create tools objects");
  }

  /**
   * Создает объект исходящего сообщения, и заполняет его необходимыми данными
   * @param message пришедшее сообщение
   * @return новое исходящее сообщение
   */
  public static SendMessage makeSendMessage(MessageDto message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(message.getChatId().toString());
    return sendMessage;
  }

  /**
   * Делает клавиатуру из массива строк, устанавливает ее в исходящее сообщение
   */
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

  /**
   * Устанавливает дефолтную клавиатуру в исх сообщение
   */
  public static void removeSpecialKeyboard(SendMessage sendMessage) {
    ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
    replyKeyboardRemove.setRemoveKeyboard(true);
    sendMessage.setReplyMarkup(replyKeyboardRemove);
  }

  /**
   * Отправляет сообщение
   */
  public static void sendMessage(SendMessage sendMessage, TelegramLongPollingBot botToSend) {
    try {
      botToSend.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(e.getMessage(), e);
    }
  }
}
