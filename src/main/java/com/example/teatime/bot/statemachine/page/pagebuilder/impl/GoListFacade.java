package com.example.teatime.bot.statemachine.page.pagebuilder.impl;

import com.example.teatime.bot.statemachine.page.pagebuilder.api.MessagePart;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;

import java.util.function.Function;

public class GoListFacade<T> implements MessagePart {
  private String head;
  private Iterable<T> list;
  private Function<T, String> toTextFunction;
  private Function<T, Long> toIdFunction;
  private String noFoundMessage;


  public GoListFacade() {
  }

  public GoListFacade<T> setHead(String head) {
    this.head = head;
    return this;
  }

  public GoListFacade<T> setList(Iterable<T> list) {
    this.list = list;
    return this;
  }

  public GoListFacade<T> setToTextFunction(Function<T, String> toTextFunction) {
    this.toTextFunction = toTextFunction;
    return this;
  }

  public GoListFacade<T> setToIdFunction(Function<T, Long> toIdFunction) {
    this.toIdFunction = toIdFunction;
    return this;
  }

  public GoListFacade<T> setNoFoundMessage(String noFoundMessage) {
    this.noFoundMessage = noFoundMessage;
    return this;
  }

  @Override
  public String toText() {
    if (!list.iterator().hasNext()) {
      return noFoundMessage;
    }

    StringBuilder message = new StringBuilder(head + "\n" + "\n");

    for (T elem : list) {
      String elemName = toTextFunction.apply(elem);
      Long id = toIdFunction.apply(elem);

      message
        .append(elemName).append("\n")
        .append("Перейти - ").append(LinkTransitions.GO.getPrefix()).append(id).append("\n")
        .append("\n");
    }
    return message.toString();
  }
}
