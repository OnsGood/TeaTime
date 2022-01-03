package com.example.teatime.bot.statemachine.page.pagebuilder.impl;

import com.example.teatime.bot.statemachine.page.pagebuilder.api.MessagePart;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;

public class DeleteLink implements MessagePart {
  private final Long id;

  public DeleteLink(Long id) {
    this.id = id;
  }

  @Override
  public String toText() {
    return "Удалить - " + LinkTransitions.DELETE.makeLink(id) + "\n";
  }
}
