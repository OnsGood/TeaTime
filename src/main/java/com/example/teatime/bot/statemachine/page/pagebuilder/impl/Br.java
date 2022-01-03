package com.example.teatime.bot.statemachine.page.pagebuilder.impl;

import com.example.teatime.bot.statemachine.page.pagebuilder.api.MessagePart;

public class Br implements MessagePart {
  @Override
  public String toText() {
    return "\n";
  }
}
