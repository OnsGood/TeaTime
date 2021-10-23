package com.example.teatime.bot.life;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public interface MessageResolver {

  void resolveUpdate(Update update, TelegramLongPollingBot pollingBot);

}
