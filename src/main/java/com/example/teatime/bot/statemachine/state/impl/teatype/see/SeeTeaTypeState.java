package com.example.teatime.bot.statemachine.state.impl.teatype.see;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.list.TeaListFromTeaTypePage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.tea.list.TeaListFromTeaTypeState;

@Component
public class SeeTeaTypeState extends AbstractState {
  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void catchIdGo(Message message, StateMachine stateMachine) {
    stateMachine.setState(TeaListFromTeaTypeState.class);
    getPageManager().sendPageMessage(TeaListFromTeaTypePage.class, message, stateMachine);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
