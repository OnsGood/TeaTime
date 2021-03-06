package com.example.teatime.bot.statemachine.transition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация вешается на сеттер MessageIdentifier <br>
 * Только для StateMachine <br>
 * Позволяет динамически установить все идентифаеры для всех аннотированных методов State <br>
 *
 * @author sergey
 * @see com.example.teatime.bot.statemachine.StateMachine
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectStateIdentifiersByMarkedMethods {
}
