package com.example.teatime.bot.statemachine.page.pagebuilder.impl;

import com.example.teatime.bot.statemachine.page.pagebuilder.api.MessagePart;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;

public class EditLink implements MessagePart {
  private final Long id;

  public EditLink(Long id) {
    this.id = id;
  }

  @Override
  public String toText() {
    return "Редактировать - " + LinkTransitions.EDIT.makeLink(id) + "\n";
  }
}