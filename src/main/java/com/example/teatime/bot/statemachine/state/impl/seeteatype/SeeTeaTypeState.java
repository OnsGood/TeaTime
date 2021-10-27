package com.example.teatime.bot.statemachine.state.impl.seeteatype;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.list.TeaListFromTeaTypePage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.tealist.TeaListFromTeaTypeState;
import com.example.teatime.bot.statemachine.state.impl.teatypelist.MainPageState;

@Component
public class SeeTeaTypeState extends AbstractState {
  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void catchTeaTypeId(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(TeaListFromTeaTypeState.class));
    MessageTools.sendMessage(getPageManager().getPage(TeaListFromTeaTypePage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
