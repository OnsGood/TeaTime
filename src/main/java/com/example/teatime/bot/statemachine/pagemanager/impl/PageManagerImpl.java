package com.example.teatime.bot.statemachine.pagemanager.impl;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.pagemanager.api.PageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;

@Component
public class PageManagerImpl implements PageManager {
  private Page[] pages;

  @Autowired
  public void setPages(Page[] pages) {
    this.pages = pages;
  }

  @Override
  public Page getPage(Class<? extends Page> pageClass) {
    Assert.notNull(pageClass, "pageClass must be not null");
    return Arrays.stream(pages)
      .filter(s -> s.getClass().equals(pageClass))
      .findAny()
      .orElseThrow(() -> new RuntimeException("Page not found: " + pageClass.getSimpleName()));
  }

  @Override
  public void sendPageMessage(Class<? extends Page> pageClass, MessageDto receivedMessage, StateMachine stateMachine) {
    MessageTools.sendMessage(getPage(pageClass).getPageMessage(receivedMessage, stateMachine), stateMachine.getBot());
  }
}
