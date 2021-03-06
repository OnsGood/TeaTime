package com.example.teatime.bot.statemachine.state.impl.teatype.list;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.state.api.State;
import org.springframework.stereotype.Component;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.insubd.CreateTeaTypePage;
import com.example.teatime.bot.statemachine.page.impl.teatype.list.TeaTypeListPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.see.SeeTeaTypePage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.teatype.insubd.CreateTeaTypeState;
import com.example.teatime.bot.statemachine.state.impl.teatype.see.SeeTeaTypeState;

@Component("TeaTypeListState")
public class TeaTypeListState extends AbstractState implements State {

  @Override
  @Historical
  public void catchIdGo(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(SeeTeaTypeState.class);
    sendPageMessage(SeeTeaTypePage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(MessageDto message, StateMachine stateMachine) {
    TeaType teaType = new TeaType();
    stateMachine.getDataManager().setObject(DataKeys.TEA_TYPE, teaType);
    stateMachine.setState(CreateTeaTypeState.class);
    sendPageMessage(CreateTeaTypePage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void listTeaTypes(MessageDto message, StateMachine stateMachine) {
    sendPageMessage(TeaTypeListPage.class, message, stateMachine);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
