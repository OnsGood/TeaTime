package com.example.teatime.bot.statemachine.state.api;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataSupportable;
import com.example.teatime.bot.statemachine.transition.KeyTransitionMark;
import com.example.teatime.bot.statemachine.transition.LinkTransitionMark;

import static com.example.teatime.bot.statemachine.transition.KeyTransitions.*;
import static com.example.teatime.bot.statemachine.transition.LinkTransitions.*;

/**
 * Основной интерфейс состояний. <br>
 * Для добавления нового действия, необходимо написать новый метод, и транзишены для него. <br>
 * Для добавления нового состояния необходимо написать новую имплементацию, и определить нужные методы. <br>
 *
 * @see com.example.teatime.bot.statemachine.transition.Transition
 * @see KeyTransitionMark
 * @see LinkTransitionMark
 */
public interface State extends DataSupportable {

  /**
   * Пришла команда на список всех типов чаев
   */
  @KeyTransitionMark(keyTransition = TEA_TYPE_LIST)
  void listTeaTypes(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на главную страницу
   */
  @KeyTransitionMark(keyTransition = MAIN_PAGE)
  void mainPage(Message message, StateMachine stateMachine);

  /**
   * Пришла команда назад
   */
  @KeyTransitionMark(keyTransition = BACK)
  void back(Message message, StateMachine stateMachine);


  /**
   * Пришел id с намеком на переход
   */
  @LinkTransitionMark(linkTransition = GO)
  void catchIdGo(Message message, StateMachine stateMachine);

  /**
   * Пришел id с намеком на еще один переход
   */
  @LinkTransitionMark(linkTransition = GO1)
  void catchIdGo1(Message message, StateMachine stateMachine);

  /**
   * Пришел id, с намеком на редактирование
   */
  @LinkTransitionMark(linkTransition = EDIT)
  void catchIdEdit(Message message, StateMachine stateMachine);

  /**
   * Пришел id, с намеком на удаление
   */
  @LinkTransitionMark(linkTransition = DELETE)
  void catchIdDelete(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на поиск чая по имени
   */
  @KeyTransitionMark(keyTransition = TEA_NAME_SEARCH)
  void listTeaFromName(Message message, StateMachine stateMachine);


  /**
   * Пришла команда на создание/изменение объекта
   */
  @KeyTransitionMark(keyTransition = {CREATE_TEA, CREATE_TEA_TYPE, CREATE_BOILING, SAVE})
  void insupd(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на ввод названия
   */
  @KeyTransitionMark(keyTransition = {SET_TITLE, CHANGE_TITLE})
  void setTitle(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на ввод описания
   */
  @KeyTransitionMark(keyTransition = {SET_DESCR, CHANGE_DESCR})
  void setDescr(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на ввод типа чая
   */
  @KeyTransitionMark(keyTransition = {SET_TYPE, CHANGE_TYPE})
  void setType(Message message, StateMachine stateMachine);

  /**
   * Пришла команда на ввод чая
   */
  @KeyTransitionMark(keyTransition = {SET_TEA, CHANGE_TEA})
  void setTea(Message message, StateMachine stateMachine);

  /**
   * Обработать неизвестную команду
   */
  void unknownMessage(Message message, StateMachine machine);
}
