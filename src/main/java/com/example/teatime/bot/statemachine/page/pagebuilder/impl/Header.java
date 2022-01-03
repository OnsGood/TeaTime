package com.example.teatime.bot.statemachine.page.pagebuilder.impl;

import com.example.teatime.bot.statemachine.page.pagebuilder.api.MessagePart;

public class Header implements MessagePart {
  String text;

  public Header(String text) {
    this.text = text;
  }

  public Header(String... texts) {
    this.text = String.join("",texts);
  }

  @Override
  public String toText() {
    return text + "\n";
  }
}
