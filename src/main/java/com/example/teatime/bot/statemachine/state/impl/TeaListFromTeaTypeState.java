package com.example.teatime.bot.statemachine.state.impl;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.createtea.CreateTeaPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.TeaTypeListPage;
import com.example.teatime.bot.statemachine.state.impl.createtea.CreateTeaState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class TeaListFromTeaTypeState extends AbstractState {
  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void back(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(TeaTypeListState.class));
    MessageTools.sendMessage(getPageManager().getPage(TeaTypeListPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void createTea(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    Tea tea = new Tea();
    stateMachine.getDataManager().setObject(DataKeys.CREATED_TEA, tea);
    stateMachine.setState(getStateManager().getState(CreateTeaState.class));
    MessageTools.sendMessage(getPageManager().getPage(CreateTeaPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
