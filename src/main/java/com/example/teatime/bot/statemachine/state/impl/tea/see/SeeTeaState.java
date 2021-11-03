package com.example.teatime.bot.statemachine.state.impl.tea.see;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.list.BoilingListFromTeaPage;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.EditTeaPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.list.BoilingListFromTeaState;
import com.example.teatime.bot.statemachine.state.impl.tea.delete.DeleteTeaState;
import com.example.teatime.bot.statemachine.state.impl.tea.insubd.CreateTeaState;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaService;

@Component
public class SeeTeaState extends AbstractState {
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
    stateMachine.getDataManager().setObject(DataKeys.TEA, tea);
    stateMachine.setState(DeleteTeaState.class);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
