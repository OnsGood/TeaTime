package com.example.teatime.bot.statemachine;

import com.example.teatime.bot.life.MessageDto;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
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
    throw new IllegalStateException("Not allowed create tools objects");
  }

  /**
   * Создает объект исходящего сообщения, и заполняет его необходимыми данными
   *
   * @param message пришедшее сообщение
   * @return новое исходящее сообщение
   */
  public static SendMessage makeSendMessage(MessageDto message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(message.chatId().toString());
    return sendMessage;
  }

  public static EditMessageText makeCallbackMessage(MessageDto message) {
    EditMessageText sendMessage = new EditMessageText();
    sendMessage.setChatId(message.chatId().toString());
    sendMessage.setMessageId(message.messageId());
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
   * Делает клавиатуру из массива строк, устанавливает ее в исходящее сообщение
   */
  public static void setKeyboard(String[][] usualKeys, String[][] moderatorKeys, SendMessage sendMessage, boolean isUserModerator) {
    if (isUserModerator) {
      setKeyboard(moderatorKeys, sendMessage);
    } else {
      setKeyboard(usualKeys, sendMessage);
    }
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
  public static void sendMessage(BotApiMethod<?> sendMessage, TelegramLongPollingBot botToSend) {
    try {
      botToSend.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(e.getMessage(), e);
    }
  }
}
