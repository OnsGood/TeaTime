package com.example.teatime.bot.statemachine.page.pagebuilder;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.page.pagebuilder.api.MessagePart;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class PageMessageBuilder {
  private final List<MessagePart> messageParts = new ArrayList<>();
  private final MessageDto receivedMessage;
  private String[][] keyboard;
  private boolean removeKeyboard;

  public PageMessageBuilder(MessageDto receivedMessage) {
    this.receivedMessage = receivedMessage;
  }

  public PageMessageBuilder part(MessagePart messagePart) {
    messageParts.add(messagePart);
    return this;
  }

  public PageMessageBuilder keyboard(String[][] keyboard) {
    this.keyboard = keyboard;
    return this;
  }

  public PageMessageBuilder removeKeyboard() {
    removeKeyboard = true;
    return this;
  }

  public String buildMessageText() {
    return messageParts.stream()
      .map(MessagePart::toText)
      .reduce("", (partialString, element) -> partialString + element);
  }

  public List<SendMessage> buildMessageList() {
    SendMessage sendMessage = MessageTools.makeSendMessage(receivedMessage);

    if (removeKeyboard) {
      MessageTools.removeSpecialKeyboard(sendMessage);
    } else if (keyboard != null) {
      MessageTools.setKeyboard(keyboard, sendMessage);
    }

    sendMessage.setText(buildMessageText());
    return List.of(sendMessage);
  }
}