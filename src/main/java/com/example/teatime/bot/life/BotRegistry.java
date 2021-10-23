package com.example.teatime.bot.life;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class BotRegistry {
  private static final Logger log = Logger.getLogger(BotRegistry.class);
  private static final int RECONNECT_PAUSE = 10000;

  private TelegramLongPollingBot[] botConnectors;

  @Autowired
  public void setBotConnectors(TelegramLongPollingBot[] botConnectors) {
    this.botConnectors = botConnectors;
  }

  @PostConstruct
  public void registerBot() {
    Arrays.stream(botConnectors)
            .forEach(this::connectBot);
  }

  public void connectBot(TelegramLongPollingBot botConnector) {
    try {
      TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
      telegramBotsApi.registerBot(botConnector);
      log.info("TelegramAPI started. Look for messages");
    } catch (TelegramApiException e) {
      log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
      try {
        Thread.sleep(RECONNECT_PAUSE);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
        return;
      }
      connectBot(botConnector);
    }
  }
}
