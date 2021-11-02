package com.example.teatime.bot.statemachine.state.impl.teatype.insubd;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.insubd.CreateTeaTypePage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;

@Component
public class InputTeaTypeNameState extends AbstractState {

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void createTeaType(Message message, StateMachine stateMachine) {
  }

  @Override
  public void unknownMessage(Message message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaTypeState.class);
    TeaType teaType = stateMachine.getDataManager().getObject(DataKeys.MODIFIED_TEA_TYPE, TeaType.class);
    teaType.setTitle(message.getText());
    getPageManager().sendPageMessage(CreateTeaTypePage.class, message, stateMachine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(DataKeys.MODIFIED_TEA_TYPE);
  }


  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
