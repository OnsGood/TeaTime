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
   * Пришла команда на список всех типов чаев
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.TEA_TYPE_LIST)
  void listTeaTypes(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на главную страницу
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.MAIN_PAGE)
  void mainPage(Message message, StateMachine stateMachine);

  /**
   * Пришла команда назад
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.BACK)
  void back(Message message, StateMachine stateMachine);


  /**
   * Пришел id типа чая
   */
  @LinkTransitionMark(linkTransition = LinkTransitions.TEA_TYPE)
  void catchTeaTypeId(Message message, StateMachine stateMachine);

  /**
   * Пришел id чая
   */
  @LinkTransitionMark(linkTransition = LinkTransitions.TEA)
  void catchTeaId(Message message, StateMachine stateMachine);

  /**
   * Пришел id, с намеком на редактирование
   */
  @LinkTransitionMark(linkTransition = LinkTransitions.EDIT)
  void catchIdEdit(Message message, StateMachine stateMachine);

  /**
   * Пришел id, с намеком на удаление
   */
  @LinkTransitionMark(linkTransition = LinkTransitions.DELETE)
  void catchIdDelete(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на поиск чая по имени
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.TEA_NAME_SEARCH)
  void listTeaFromName(Message message, StateMachine stateMachine);


  /**
   * Пришла команда на создание чая
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.CREATE_TEA)
  void createTea(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на ввод названия
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.SET_TITLE)
  void setTitle(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на ввод описания
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.SET_DESCR)
  void setDescr(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на ввод типа чая
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.SET_TYPE)
  void setType(Message message, StateMachine stateMachine);


  /**
   * Пришла команда на создание типа чая
   */
  @KeyTransitionMark(keyTransition = KeyTransitions.CREATE_TEA_TYPE)
  void createTeaType(Message message, StateMachine stateMachine);

  /**
   * Обработать неизвестную команду
   */
  void unknownMessage(Message message, StateMachine machine);
}
