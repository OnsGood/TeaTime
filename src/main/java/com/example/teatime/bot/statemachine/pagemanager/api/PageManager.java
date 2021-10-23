package com.example.teatime.bot.statemachine.pagemanager.api;

import com.example.teatime.bot.statemachine.page.api.Page;

public interface PageManager {
  Page getPage(Class<? extends Page> stateClass);
}
