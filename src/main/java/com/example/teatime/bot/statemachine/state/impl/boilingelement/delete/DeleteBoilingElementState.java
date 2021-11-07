package com.example.teatime.bot.statemachine.state.impl.boilingelement.delete;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.DeleteSuccessPage;
import com.example.teatime.bot.statemachine.page.impl.DeleteUnsuccessfulPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.see.SeeBoilingFromDataManagerPage;
import com.example.teatime.bot.statemachine.page.impl.boilingelement.delete.DeleteNoDeletePage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.see.SeeBoilingState;
import com.example.teatime.service.api.BoilingElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Set;

@Component("DeleteBoilingElementState")
public class DeleteBoilingElementState extends AbstractState implements State {
  private BoilingElementService boilingElementService;

  @Autowired
  public void setBoilingElementService(BoilingElementService boilingElementService) {
    this.boilingElementService = boilingElementService;
  }

  @Override
  @Historical
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void unknownMessage(Message message, StateMachine stateMachine) {
    Boiling boiling = stateMachine.getDataManager().getObject(DataKeys.BOILING, Boiling.class);
    if ("Да".equals(message.getText())) {
      boilingElementService.findElementMaxNumberByBoiling(boiling)
        .ifPresentOrElse(
          be -> {
            boilingElementService.delete(be);
            getPageManager().sendPageMessage(DeleteSuccessPage.class, message, stateMachine);
          },
          () -> getPageManager().sendPageMessage(DeleteNoDeletePage.class, message, stateMachine)
        );
    } else {
      getPageManager().sendPageMessage(DeleteUnsuccessfulPage.class, message, stateMachine);
    }
    getPageManager().sendPageMessage(SeeBoilingFromDataManagerPage.class, message, stateMachine);
    stateMachine.setState(SeeBoilingState.class);
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
