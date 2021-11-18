package com.example.teatime.bot.statemachine.state.impl.tea.list;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.see.SeeTeaPage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.tea.see.SeeTeaState;

public abstract class AbstractTeaListState extends AbstractState implements State {
  @Override
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }


  @Override
  public void catchIdGo(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(SeeTeaState.class);
    getPageManager().sendPageMessage(SeeTeaPage.class, message, stateMachine);
  }

}
