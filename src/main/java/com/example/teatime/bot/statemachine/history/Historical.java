package com.example.teatime.bot.statemachine.history;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация ставится на те методы тех состояний, которые нужно записать в историю
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Historical {
}
