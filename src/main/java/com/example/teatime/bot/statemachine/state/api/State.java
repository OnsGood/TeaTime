package com.example.teatime.bot.statemachine.state.api;

import com.example.teatime.bot.statemachine.transition.KeyTransitionMark;
import com.example.teatime.bot.statemachine.transition.LinkTransitionMark;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Основной интерфейс состояний. <br>
 * Для добавления нового действия, необходимо написать новый метод, и транзишен для него. <br>
 * Для добавления нового состояния необходимо написать новую имплементацию, и определить нужные методы. <br>
 */
public interface State {

  /**
   * Получить страницу со списком всех типов чаев
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.TEA_TYPE_LIST)
  void listTeaTypes(Message message, StateMachine stateMachine);

  /**
   * Получить главную страницу
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.MAIN_PAGE)
  void mainPage(Message message, StateMachine stateMachine);

  /**
   * Получить страницу на которой были раньше
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.BACK)
  void back(Message message, StateMachine stateMachine);

  /**
   * Получить страницу со списком чаев, по типу чая
   */
  @LinkTransitionMark(linkTransition = LinkTransitions.GET_TEA_TYPE)
  void listTeaFromTeaType(Message message, StateMachine stateMachine);

  /**
   * Обработать неизвестную команду
   */
  void unknownMessage(Message message, StateMachine machine);
}
