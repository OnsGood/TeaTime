package com.example.teatime.bot.statemachine.state.impl.tea.see;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.DeleteWithNameInputPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.list.BoilingListFromTeaPage;
import com.example.teatime.bot.statemachine.page.impl.tea.delete.DeleteTeaHasCichlidsPage;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.EditTeaPage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.list.BoilingListFromTeaState;
import com.example.teatime.bot.statemachine.state.impl.tea.delete.DeleteTeaState;
import com.example.teatime.bot.statemachine.state.impl.tea.insubd.CreateTeaState;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaService;

@Component("SeeTeaState")
public class SeeTeaState extends AbstractState implements State {
  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void catchIdGo(MessageDto message, StateMachine stateMachine) {
    Tea tea = teaService.getTeaById(LinkTransitions.getIdFromLink(message.text()));
    stateMachine.getDataManager().setObject(DataKeys.TEA, tea);
    stateMachine.setState(BoilingListFromTeaState.class);
    sendPageMessage(BoilingListFromTeaPage.class, message, stateMachine);
  }

  @Override
  public void catchIdEdit(MessageDto message, StateMachine stateMachine) {
    Tea tea = teaService.getTeaById(LinkTransitions.getIdFromLink(message.text()));
    stateMachine.getDataManager().setObject(DataKeys.TEA, tea);
    stateMachine.setState(CreateTeaState.class);
    sendPageMessage(EditTeaPage.class, message, stateMachine);
  }

  @Override
  public void catchIdDelete(MessageDto message, StateMachine stateMachine) {
    Tea tea = teaService.getTeaById(LinkTransitions.getIdFromLink(message.text()));
    if (teaService.isAllowedToDelete(tea)) {
      stateMachine.getDataManager().setObject(DataKeys.TEA, tea);
      stateMachine.setState(DeleteTeaState.class);
      sendPageMessage(DeleteWithNameInputPage.class, message, stateMachine);
    } else {
      sendPageMessage(DeleteTeaHasCichlidsPage.class, message, stateMachine);
      stateMachine.getDialogHistory().goToCurrentState(stateMachine);
    }
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
