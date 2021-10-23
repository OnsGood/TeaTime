package com.example.teatime.bot.statemachine.pagemanager.api;

import com.example.teatime.bot.statemachine.page.api.Page;

/**
 * Класс помощник для получения бинов страниц по их классам
 *
 * @apiNote разрешено использовать только там, где можно знать о реализациях страниц
 */
public interface PageManager {
  Page getPage(Class<? extends Page> stateClass);
}
