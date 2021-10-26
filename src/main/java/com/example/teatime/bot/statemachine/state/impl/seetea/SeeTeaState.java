package com.example.teatime.bot.statemachine.state.impl.seetea;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boilinglist.BoilingListFromTeaPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.boilinglist.BoilingListFromTeaState;
import com.example.teatime.bot.statemachine.state.impl.teatypelist.MainPageState;

@Component
public class SeeTeaState extends AbstractState {

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void catchTeaId(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(BoilingListFromTeaState.class));
    MessageTools.sendMessage(getPageManager().getPage(BoilingListFromTeaPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  /*@Override
  public void catchTeaIdEdit(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(EditTeaState.class));
    MessageTools.sendMessage(getPageManager().getPage(EditTeaPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }*/

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
