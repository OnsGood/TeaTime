package com.example.teatime.bot.statemachine.history;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.state.api.State;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

import static java.util.Objects.nonNull;

@Component
public class HistoricalBeanPostProcessor implements BeanPostProcessor {
  private final Map<String, Data> map = new HashMap<>();

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (!(bean instanceof State)) {
      return bean;
    }
    Data data = new Data(bean.getClass(), new HashSet<>());
    for (Method method : bean.getClass().getMethods()) {
      if (nonNull(method.getAnnotation(Historical.class))) {
        data.addMethod(method.getName());
      }
    }
    map.put(beanName, data);
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Data data = map.get(beanName);
    if (nonNull(data) && !data.methods.isEmpty()) {
      return Proxy.newProxyInstance(data.beanClass().getClassLoader(), data.beanClass().getInterfaces(), new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          if (Objects.nonNull(args)) {
            StateMachine machine = (StateMachine) Arrays.stream(args)
              .filter(StateMachine.class::isInstance)
              .findAny()
              .orElse(null);

            Message message = (Message) Arrays.stream(args)
              .filter(Message.class::isInstance)
              .findAny()
              .orElse(null);

            if (Objects.nonNull(machine) && Objects.nonNull(message)) {
              machine.getDialogHistory().newUntrackedHistory((State) bean, message);

              if (data.isMethodAnnotated(method.getName())) {
                machine.getDialogHistory().newHistory((State) bean, message);
              }
            }
          }

          return ReflectionUtils.invokeMethod(method, bean, args);
        }
      });
    }
    return bean;
  }

  private record Data(Class<?> beanClass, Set<String> methods) {
    boolean isMethodAnnotated(String method) {
      return methods.contains(method);
    }

    void addMethod(String method) {
      methods.add(method);
    }

  }
}
