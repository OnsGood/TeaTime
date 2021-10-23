package com.example.teatime.bot.statemachine.transition;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.Method;
import java.util.function.Function;

import static java.util.Objects.nonNull;

public class TransitionMarkProcessor {

  public Function<Message, Boolean> getIdentifyFunction(Method method) {
    if (nonNull(method.getAnnotation(KeyTransitionMark.class))) {
      Transition transition = method.getAnnotation(KeyTransitionMark.class).keyTransition();
      return message -> transition.match(message.getText());
    }
    if (nonNull(method.getAnnotation(LinkTransitionMark.class))) {
      Transition transition = method.getAnnotation(LinkTransitionMark.class).linkTransition();
      return message -> transition.match(message.getText());
    }
    return null;
  }
}
