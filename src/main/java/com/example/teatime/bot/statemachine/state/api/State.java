package com.example.teatime.bot.statemachine.state.api;

import com.example.teatime.bot.statemachine.transition.KeyTransitionMark;
import com.example.teatime.bot.statemachine.transition.LinkTransitionMark;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface State {

  @KeyTransitionMark(keyTransition = KeyTransitions.TEA_TYPE_LIST)
  void listTeaTypes(Message message, StateMachine stateMachine);

  @KeyTransitionMark(keyTransition = KeyTransitions.MAIN_PAGE)
  void mainPage(Message message, StateMachine stateMachine);

  @KeyTransitionMark(keyTransition = KeyTransitions.BACK)
  void back(Message message, StateMachine stateMachine);

  @LinkTransitionMark(linkTransition = LinkTransitions.GET_TEA_TYPE)
  void listTeaFromTeaType(Message message, StateMachine stateMachine);

  void unknownMessage(Message message, StateMachine machine);
}
