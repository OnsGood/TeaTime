package com.example.teatime.bot.statemachine.pagemanager.api;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;

/**
 * Посредник для получения бинов страниц по их классам
 *
 * @apiNote разрешено использовать только там, где можно знать о реализациях страниц
 */
public interface PageManager {
  Page getPage(Class<? extends Page> pageClass);

  void sendPageMessage(Class<? extends Page> pageClass, Message receivedMessage, StateMachine stateMachine);
}
