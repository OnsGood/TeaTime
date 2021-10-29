package com.example.teatime.bot.statemachine.state.impl.teatype.insubd;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.insubd.CreateTeaTypePage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class InputTeaTypeNameState extends AbstractState {

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void createTeaType(Message message, StateMachine stateMachine) {
  }

  @Override
  public void unknownMessage(Message message, StateMachine stateMachine) {
    stateMachine.setState(getStateManager().getState(CreateTeaTypeState.class));
    TeaType teaType = stateMachine.getDataManager().getObject(DataKeys.MODIFIED_TEA_TYPE, TeaType.class);
    teaType.setTitle(message.getText());
    MessageTools.sendMessage(getPageManager().getPage(CreateTeaTypePage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
