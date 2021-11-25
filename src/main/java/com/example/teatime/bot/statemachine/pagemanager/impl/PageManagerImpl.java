package com.example.teatime.bot.statemachine.pagemanager.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.callback.CallbackDataDto;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.pagemanager.api.PageManager;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class PageManagerImpl implements PageManager {
  private Map<String, Page> pages;

  @Autowired
  public void setPages(Page[] pages) {
    this.pages = Arrays.stream(pages)
      .collect(Collectors.toMap(p -> p.getClass().getSimpleName(), p -> p));
  }

  @Override
  public Page getPage(Class<? extends Page> pageClass) {
    Assert.notNull(pageClass, "pageClass must be not null");
    return getPage(pageClass.getSimpleName());
  }

  private Page getPage(String pageName) {
    Assert.notNull(pageName, "pageName must be not null");
    return Optional.ofNullable(pages.get(pageName))
      .orElseThrow(() -> new RuntimeException("Page not found: " + pageName));
  }

  @Override
  public void sendPageMessage(Class<? extends Page> pageClass, MessageDto receivedMessage, StateMachine stateMachine) {
    getPage(pageClass).getPageMessage(receivedMessage, stateMachine).stream()
      .filter(Objects::nonNull)
      .forEach(m -> sendMessage(m, stateMachine.getBot()));
  }

  @Override
  public void sendCallbackResponse(MessageDto receivedMessage, StateMachine stateMachine) {
    CallbackDataDto callbackDataDto = CallbackDataDto.callbackDataToDto(receivedMessage.getText());
    sendMessage(getPage(callbackDataDto.getOriginalBeanName()).getCallbackResponse(receivedMessage, stateMachine), stateMachine.getBot());
  }
}
