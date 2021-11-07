package com.example.teatime.bot.statemachine.state.impl.tea.list;

import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.state.api.State;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.tea.list.InputTeaNamePage;
import com.example.teatime.bot.statemachine.page.impl.tea.list.TeaListFromNamePage;

@Component("TeaListFromNameState")
public class TeaListFromNameState extends AbstractTeaListState implements State {

  @Override
  @Historical
  public void unknownMessage(Message message, StateMachine stateMachine) {
    getPageManager().sendPageMessage(TeaListFromNamePage.class, message, stateMachine);
  }

  @Override
  public void listTeaFromName(Message message, StateMachine stateMachine) {
    getPageManager().sendPageMessage(InputTeaNamePage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void mainPage(Message message, StateMachine stateMachine) {
    super.mainPage(message, stateMachine);
  }

  @Override
  @Historical
  public void catchIdGo(Message message, StateMachine stateMachine) {
    super.catchIdGo(message, stateMachine);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
