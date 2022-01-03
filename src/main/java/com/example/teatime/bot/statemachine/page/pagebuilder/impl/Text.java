package com.example.teatime.bot.statemachine.page.pagebuilder.impl;

import com.example.teatime.bot.statemachine.page.pagebuilder.api.MessagePart;

public class Text implements MessagePart {
  String string;

  public Text(String string) {
    this.string = string;
  }

  public Text(String... texts) {
    this.string = String.join("", texts);
  }

  @Override
  public String toText() {
    return string + "\n";
  }
}
