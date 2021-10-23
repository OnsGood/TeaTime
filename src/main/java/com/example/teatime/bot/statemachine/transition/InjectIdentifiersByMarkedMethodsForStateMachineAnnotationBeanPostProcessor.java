package com.example.teatime.bot.statemachine.transition;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.identifier.MessageIdentifier;
import com.example.teatime.bot.statemachine.state.api.State;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.Method;
import java.util.function.Function;

import static java.util.Objects.nonNull;

@Component
public class InjectIdentifiersByMarkedMethodsForStateMachineAnnotationBeanPostProcessor implements BeanPostProcessor {
  private static final Logger log = Logger.getLogger(InjectIdentifiersByMarkedMethodsForStateMachineAnnotationBeanPostProcessor.class);

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (!(bean instanceof StateMachine stateMachine)) {
      return bean;
    }

    for (Method stateMachineMethod : bean.getClass().getDeclaredMethods()) {
      InjectStateIdentifiersByMarkedMethods annotation = stateMachineMethod.getAnnotation(InjectStateIdentifiersByMarkedMethods.class);
      if (annotation != null) {
        MessageIdentifier messageIdentifier = new MessageIdentifier();
        for (Method markedStateMethod : State.class.getDeclaredMethods()) {
          Function<Message, Boolean> identifyFunction = getIdentifyFunction(markedStateMethod);
          if (identifyFunction != null) {
            messageIdentifier.addIdentifier(
                identifyFunction,
                message -> ReflectionUtils.invokeMethod(markedStateMethod, stateMachine.getState(), message, stateMachine)
            );
          }
        }
        log.info("init Identifiers from State");
        ReflectionUtils.invokeMethod(stateMachineMethod, stateMachine, messageIdentifier);
      }
    }

    return bean;
  }


  private Function<Message, Boolean> getIdentifyFunction(Method method) {
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
