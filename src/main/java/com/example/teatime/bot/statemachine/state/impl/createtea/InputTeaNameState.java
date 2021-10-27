package com.example.teatime.bot.statemachine.state.impl.createtea;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.create.CreateTeaPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.teatypelist.MainPageState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class InputTeaNameState extends AbstractState {

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void createTea(Message message, StateMachine stateMachine) {
    stateMachine.setState(getStateManager().getState(CreateTeaState.class));
    MessageTools.sendMessage(getPageManager().getPage(CreateTeaPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void unknownMessage(Message message, StateMachine stateMachine) {
    stateMachine.setState(getStateManager().getState(CreateTeaState.class));
    Tea tea = stateMachine.getDataManager().getObject(DataKeys.CREATED_TEA, Tea.class);
    tea.setTitle(message.getText());
    MessageTools.sendMessage(getPageManager().getPage(CreateTeaPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
