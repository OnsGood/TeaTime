package com.example.teatime.bot.statemachine.page.pagebuilder.impl;

import com.example.teatime.bot.statemachine.page.pagebuilder.api.MessagePart;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;

public class GoLink implements MessagePart {
  private final Long id;
  private final String message;


  public GoLink(String message, Long id) {
    this.id = id;
    this.message = message;
  }

  @Override
  public String toText() {
    return message + " - " + LinkTransitions.GO.makeLink(id) + "\n";
  }
}