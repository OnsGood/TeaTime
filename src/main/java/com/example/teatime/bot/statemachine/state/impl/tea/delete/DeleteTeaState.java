package com.example.teatime.bot.statemachine.state.impl.tea.delete;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.DeleteSuccessPage;
import com.example.teatime.bot.statemachine.page.impl.DeleteUnsuccessfulPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Set;

@Component
public class DeleteTeaState extends AbstractState {
  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void unknownMessage(Message message, StateMachine machine) {
    Tea tea = machine.getDataManager().getObject(DataKeys.TEA, Tea.class);
    if (tea.getTitle().equals(message.getText())) {
      teaService.delete(tea);
      getPageManager().sendPageMessage(DeleteSuccessPage.class, message, machine);
    } else {
      getPageManager().sendPageMessage(DeleteUnsuccessfulPage.class, message, machine);
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
