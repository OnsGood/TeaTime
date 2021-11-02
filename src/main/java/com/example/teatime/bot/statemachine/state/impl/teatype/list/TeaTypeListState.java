package com.example.teatime.bot.statemachine.state.impl.teatype.list;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.MessageTools;
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

@Component
public class TeaTypeListState extends AbstractState {

  @Override
  public void catchTeaTypeId(Message message, StateMachine stateMachine) {
    stateMachine.setState(SeeTeaTypeState.class);
    getPageManager().sendPageMessage(SeeTeaTypePage.class, message, stateMachine);
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void createTeaType(Message message, StateMachine stateMachine) {
    TeaType teaType = new TeaType();
    stateMachine.getDataManager().setObject(DataKeys.MODIFIED_TEA_TYPE, teaType);
    stateMachine.setState(CreateTeaTypeState.class);
    getPageManager().sendPageMessage(CreateTeaTypePage.class, message, stateMachine);
  }

  @Override
  public void listTeaTypes(Message message, StateMachine stateMachine) {
    getPageManager().sendPageMessage(TeaTypeListPage.class, message, stateMachine);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
