package com.example.teatime.bot.statemachine.state.impl;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.list.InputTeaNamePage;
import com.example.teatime.bot.statemachine.page.impl.teatype.list.TeaTypeListPage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.tea.list.TeaListFromNameState;
import com.example.teatime.bot.statemachine.state.impl.teatype.list.TeaTypeListState;
import org.springframework.stereotype.Component;

@Component("MainPageState")
public class MainPageState extends AbstractState implements State {

  @Override
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void listTeaTypes(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(TeaTypeListState.class);
    sendPageMessage(TeaTypeListPage.class, message, stateMachine);
  }

  @Override
  public void listTeaFromName(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(TeaListFromNameState.class);
    sendPageMessage(InputTeaNamePage.class, message, stateMachine);
  }

  @Override
  public void back(MessageDto message, StateMachine stateMachine) {
    mainPage(message, stateMachine);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
