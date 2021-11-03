package com.example.teatime.bot.statemachine.state.impl.tea.list;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.see.SeeTeaPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.tea.see.SeeTeaState;

public abstract class AbstractTeaListState extends AbstractState {
  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }


  @Override
  public void catchIdGo(Message message, StateMachine stateMachine) {
    stateMachine.setState(SeeTeaState.class);
    getPageManager().sendPageMessage(SeeTeaPage.class, message, stateMachine);
  }

}
