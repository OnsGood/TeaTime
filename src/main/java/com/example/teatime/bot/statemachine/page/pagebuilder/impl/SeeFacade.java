package com.example.teatime.bot.statemachine.page.pagebuilder.impl;

import com.example.teatime.bot.statemachine.page.pagebuilder.api.MessagePart;

public class SeeFacade implements MessagePart {
  private MessagePart header;
  private MessagePart description;
  private String goMessage;
  private Long id;
  private boolean showEditAndDelete;

  public SeeFacade() {
  }

  public SeeFacade setHeader(MessagePart header) {
    this.header = header;
    return this;
  }

  public SeeFacade setDescription(MessagePart description) {
    this.description = description;
    return this;
  }

  public SeeFacade setGoMessage(String goMessage) {
    this.goMessage = goMessage;
    return this;
  }

  public SeeFacade setId(Long id) {
    this.id = id;
    return this;
  }

  public SeeFacade setShowEditAndDelete(boolean showEditAndDelete) {
    this.showEditAndDelete = showEditAndDelete;
    return this;
  }

  @Override
  public String toText() {
    StringBuilder builder = new StringBuilder()
      .append(header.toText())
      .append("\n")
      .append(description.toText());

    if (goMessage != null) {
      builder
        .append("\n")
        .append(new GoLink(goMessage, id).toText());
    }

    if (showEditAndDelete) {
      builder
        .append("\n")
        .append(new EditLink(id).toText())
        .append("\n")
        .append(new DeleteLink(id).toText());
    }

    return builder.toString();
  }
}
