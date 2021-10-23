package com.example.teatime.bot.statemachine;

import com.example.teatime.bot.statemachine.state.api.State;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface StateMachine {
  void setState(State state);

  State getState();

  void setPollingBot(TelegramLongPollingBot pollingBot);

  TelegramLongPollingBot getPollingBot();

  void resolveMessage(Message message);
}
