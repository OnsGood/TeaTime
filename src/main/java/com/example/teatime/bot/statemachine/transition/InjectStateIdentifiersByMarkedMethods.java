package com.example.teatime.bot.statemachine.transition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация вешается на сеттер MessageIdentifier
 * Только для StateMachine
 * Позволяет динамически установить все идентифаеры для всех методов State
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectStateIdentifiersByMarkedMethods {
}
