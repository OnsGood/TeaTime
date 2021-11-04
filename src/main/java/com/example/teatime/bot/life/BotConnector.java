package com.example.teatime.bot.life;

import com.example.teatime.configuration.ConfigProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotConnector extends TelegramLongPollingBot {
  private static final Logger log = Logger.getLogger(BotConnector.class);

  private MessageResolver messageResolver;
  private ConfigProvider configProvider;

  @Autowired
  public void setMessageResolver(MessageResolver messageResolver) {
    this.messageResolver = messageResolver;
  }

  @Autowired
  public void setConfigProvider(ConfigProvider configProvider) {
    this.configProvider = configProvider;
  }

  @Override
  public String getBotUsername() {
    return configProvider.get().botName();
  }

  @Override
  public String getBotToken() {
    return configProvider.get().botToken();
  }

  @Override
  public void onUpdateReceived(Update update) {
    log.info("new update recieve");
    messageResolver.resolveUpdate(update, this);
  }
}
