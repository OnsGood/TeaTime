package com.example.teatime.bot.statemachine.transition;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.identifier.MessageIdentifier;
import com.example.teatime.bot.statemachine.state.api.State;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.Objects.nonNull;

/**
 * Бин пост процессор для создания <code>MessageIdentifier</code> в машине состояний
 */
@Component
public class InjectIdentifiersByMarkedMethodsForStateMachineAnnotationBeanPostProcessor implements BeanPostProcessor {
  private static final Logger log = Logger.getLogger(InjectIdentifiersByMarkedMethodsForStateMachineAnnotationBeanPostProcessor.class);

  @Override
  public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
    if (!(bean instanceof StateMachine stateMachine)) {
      return bean;
    }

    for (Method stateMachineMethod : bean.getClass().getDeclaredMethods()) {
      InjectStateIdentifiersByMarkedMethods annotation = stateMachineMethod.getAnnotation(InjectStateIdentifiersByMarkedMethods.class);
      if (annotation != null) {
        MessageIdentifier messageIdentifier = new MessageIdentifier();
        for (Method markedStateMethod : State.class.getDeclaredMethods()) {
          getIdentifiersFromMethodTransitions(listTransitions(markedStateMethod), markedStateMethod, stateMachine)
              .forEach(messageIdentifier::addIdentifier);
        }
        log.info("init Identifiers from State");
        ReflectionUtils.invokeMethod(stateMachineMethod, stateMachine, messageIdentifier);
      }
    }

    return bean;
  }

  private List<MessageIdentifier.Identifier> getIdentifiersFromMethodTransitions(List<Transition> transitions, Method method, StateMachine stateMachine) {
    return transitions.stream()
        .map(transition -> new MessageIdentifier.Identifier(
            message -> transition.match(message.getText()),
            message -> ReflectionUtils.invokeMethod(method, stateMachine.getState(), message, stateMachine)
        ))
        .toList();
  }

  private List<Transition> listTransitions(Method method) {
    List<Transition> transitions = new ArrayList<>();
    if (nonNull(method.getAnnotation(KeyTransitionMark.class))) {
      transitions.addAll(Arrays.stream(method.getAnnotation(KeyTransitionMark.class).keyTransition()).toList());
    }
    if (nonNull(method.getAnnotation(LinkTransitionMark.class))) {
      transitions.addAll(Arrays.stream(method.getAnnotation(LinkTransitionMark.class).linkTransition()).toList());
    }
    return transitions;
  }
}
