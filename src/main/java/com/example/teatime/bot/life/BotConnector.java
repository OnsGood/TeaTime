package com.example.teatime.bot.life;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotConnector extends TelegramLongPollingBot {
  private static final Logger log = Logger.getLogger(BotConnector.class);

  private MessageResolver messageResolver;

  @Autowired
  public void setMessageResolver(MessageResolver messageResolver) {
    this.messageResolver = messageResolver;
  }


  @Override
  public String getBotUsername() {
    return "teatime0bot";
  }

  @Override
  public String getBotToken() {
    return "2032405097:AAF2V-x3Ezbpry8cWrVzDdzvojh-h_o-Grw";
  }

  @Override
  public void onUpdateReceived(Update update) {
    log.info("new update recieve");
    messageResolver.resolveUpdate(update, this);
  }
}
