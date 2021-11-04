package com.example.teatime.bot.statemachine.state.impl.tea.delete;

import java.util.Set;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.delete.DeleteTeaSuccessPage;
import com.example.teatime.bot.statemachine.page.impl.tea.delete.DeleteTeaUnsuccessfulPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DeleteTeaState extends AbstractState {
  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void unknownMessage(Message message, StateMachine machine) {
    Tea tea = machine.getDataManager().getObject(DataKeys.TEA, Tea.class);
    if (tea.getTitle().equals(message.getText())) {
      teaService.delete(tea);
      MessageTools.sendMessage(
          getPageManager().getPage(DeleteTeaSuccessPage.class).getPageMessage(message, machine),
          machine.getPollingBot()
      );
    } else {
      MessageTools.sendMessage(
          getPageManager().getPage(DeleteTeaUnsuccessfulPage.class).getPageMessage(message, machine),
          machine.getPollingBot()
      );
    }
    mainPage(message, machine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(DataKeys.TEA);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
