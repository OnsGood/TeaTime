package com.example.teatime.bot.statemachine.state.impl;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.TeaTypeListPage;
import com.example.teatime.bot.statemachine.MessageTools;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MainPageState extends AbstractState {

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message), stateMachine.getPollingBot());
  }

  @Override
  public void listTeaTypes(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(TeaTypeListState.class));
    MessageTools.sendMessage(getPageManager().getPage(TeaTypeListPage.class).getPageMessage(message), stateMachine.getPollingBot());
  }

  @Override
  public void back(Message message, StateMachine stateMachine) {
    mainPage(message, stateMachine);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
