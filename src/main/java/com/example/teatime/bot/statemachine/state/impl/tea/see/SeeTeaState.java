package com.example.teatime.bot.statemachine.state.impl.tea.see;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.Tea;
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
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void catchIdGo(Message message, StateMachine stateMachine) {
    Tea tea = teaService.getTeaById(LinkTransitions.getIdFromLink(message.getText()));
    stateMachine.getDataManager().setObject(DataKeys.TEA, tea);
    stateMachine.setState(BoilingListFromTeaState.class);
    getPageManager().sendPageMessage(BoilingListFromTeaPage.class, message, stateMachine);
  }

  @Override
  public void catchIdEdit(Message message, StateMachine stateMachine) {
    Tea tea = teaService.getTeaById(LinkTransitions.getIdFromLink(message.getText()));
    stateMachine.getDataManager().setObject(DataKeys.TEA, tea);
    stateMachine.setState(CreateTeaState.class);
    getPageManager().sendPageMessage(EditTeaPage.class, message, stateMachine);
  }

  @Override
  public void catchIdDelete(Message message, StateMachine stateMachine) {
    Tea tea = teaService.getTeaById(LinkTransitions.getIdFromLink(message.getText()));
    if (teaService.isAllowedToDelete(tea)) {
      stateMachine.getDataManager().setObject(DataKeys.TEA, tea);
      stateMachine.setState(DeleteTeaState.class);
      getPageManager().sendPageMessage(DeleteWithNameInputPage.class, message, stateMachine);
    } else {
      getPageManager().sendPageMessage(DeleteTeaHasCichlidsPage.class, message, stateMachine);
      stateMachine.getDialogHistory().goToCurrentState(stateMachine);
    }
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
