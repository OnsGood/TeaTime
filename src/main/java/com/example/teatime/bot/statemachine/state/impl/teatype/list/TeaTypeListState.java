package com.example.teatime.bot.statemachine.state.impl.teatype.list;

import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.insubd.CreateTeaTypePage;
import com.example.teatime.bot.statemachine.page.impl.teatype.see.SeeTeaTypePage;
import com.example.teatime.bot.statemachine.page.impl.teatype.list.TeaTypeListPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.teatype.insubd.CreateTeaTypeState;
import com.example.teatime.bot.statemachine.state.impl.teatype.see.SeeTeaTypeState;

@Component
public class TeaTypeListState extends AbstractState {

  @Override
  public void catchTeaTypeId(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(SeeTeaTypeState.class));
    MessageTools.sendMessage(getPageManager().getPage(SeeTeaTypePage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void createTeaType(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    TeaType teaType = new TeaType();
    stateMachine.getDataManager().setObject(DataKeys.MODIFIED_TEA_TYPE, teaType);
    stateMachine.setState(getStateManager().getState(CreateTeaTypeState.class));
    MessageTools.sendMessage(getPageManager().getPage(CreateTeaTypePage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void listTeaTypes(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    MessageTools.sendMessage(getPageManager().getPage(TeaTypeListPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
