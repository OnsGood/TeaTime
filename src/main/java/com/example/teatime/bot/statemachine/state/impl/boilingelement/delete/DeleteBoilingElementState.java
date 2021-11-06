package com.example.teatime.bot.statemachine.state.impl.boilingelement.delete;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.DeleteSuccessPage;
import com.example.teatime.bot.statemachine.page.impl.DeleteUnsuccessfulPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.see.SeeBoilingFromDataManagerPage;
import com.example.teatime.bot.statemachine.page.impl.boilingelement.delete.DeleteNoDeletePage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.see.SeeBoilingState;
import com.example.teatime.service.api.BoilingElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Set;

@Component
public class DeleteBoilingElementState extends AbstractState {
  private BoilingElementService boilingElementService;

  @Autowired
  public void setBoilingElementService(BoilingElementService boilingElementService) {
    this.boilingElementService = boilingElementService;
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void unknownMessage(Message message, StateMachine machine) {
    Boiling boiling = machine.getDataManager().getObject(DataKeys.BOILING, Boiling.class);
    if ("Да".equals(message.getText())) {
      boilingElementService.findElementMaxNumberByBoiling(boiling)
        .ifPresentOrElse(
          be -> {
            boilingElementService.delete(be);
            getPageManager().sendPageMessage(DeleteSuccessPage.class, message, machine);
          },
          () -> getPageManager().sendPageMessage(DeleteNoDeletePage.class, message, machine)
        );
    } else {
      getPageManager().sendPageMessage(DeleteUnsuccessfulPage.class, message, machine);
    }
    getPageManager().sendPageMessage(SeeBoilingFromDataManagerPage.class, message, machine);
    machine.setState(SeeBoilingState.class);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(DataKeys.BOILING);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
