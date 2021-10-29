package com.example.teatime.bot.statemachine.state.impl.tea.list;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.see.SeeTeaPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.tea.see.SeeTeaState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;

public abstract class AbstractTeaListState extends AbstractState {
  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }


  @Override
  public void catchTeaId(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(SeeTeaState.class));
    MessageTools.sendMessage(getPageManager().getPage(SeeTeaPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());

  }

  protected abstract Tea getTea();

}
