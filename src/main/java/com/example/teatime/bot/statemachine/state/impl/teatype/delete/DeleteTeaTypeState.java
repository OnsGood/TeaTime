package com.example.teatime.bot.statemachine.state.impl.teatype.delete;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.DeleteSuccessPage;
import com.example.teatime.bot.statemachine.page.impl.DeleteUnsuccessfulPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.TeaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Set;

@Component("DeleteTeaTypeState")
public class DeleteTeaTypeState extends AbstractState implements State {
  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }

  @Override
  @Historical
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void unknownMessage(Message message, StateMachine stateMachine) {
    TeaType teaType = stateMachine.getDataManager().getObject(DataKeys.TEA_TYPE, TeaType.class);
    if (teaType.getTitle().equals(message.getText())) {
      teaTypeService.delete(teaType);
      getPageManager().sendPageMessage(DeleteSuccessPage.class, message, stateMachine);
    } else {
      getPageManager().sendPageMessage(DeleteUnsuccessfulPage.class, message, stateMachine);
    }
    mainPage(message, stateMachine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(DataKeys.TEA_TYPE);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
