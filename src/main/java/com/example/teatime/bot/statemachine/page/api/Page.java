package com.example.teatime.bot.statemachine.page.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Page {

  SendMessage getPageMessage(Message receivedMessage);

}
