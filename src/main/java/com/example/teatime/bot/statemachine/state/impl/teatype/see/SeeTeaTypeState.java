package com.example.teatime.bot.statemachine.state.impl.teatype.see;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.DeleteWithNameInputPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.list.TeaListFromTeaTypePage;
import com.example.teatime.bot.statemachine.page.impl.teatype.delete.DeleteTeaTypeHasCichlidsPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.insubd.EditTeaTypePage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.tea.list.TeaListFromTeaTypeState;
import com.example.teatime.bot.statemachine.state.impl.teatype.delete.DeleteTeaTypeState;
import com.example.teatime.bot.statemachine.state.impl.teatype.insubd.CreateTeaTypeState;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaTypeService;

@Component("SeeTeaTypeState")
public class SeeTeaTypeState extends AbstractState implements State {
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
  @Historical
  public void catchIdGo(Message message, StateMachine stateMachine) {
    TeaType teaType = teaTypeService.getTeaTypeById(LinkTransitions.getIdFromLink(message.getText()));
    stateMachine.getDataManager().setObject(DataKeys.TEA_TYPE, teaType);
    stateMachine.setState(TeaListFromTeaTypeState.class);
    getPageManager().sendPageMessage(TeaListFromTeaTypePage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void catchIdEdit(Message message, StateMachine stateMachine) {
    TeaType teaType = teaTypeService.getTeaTypeById(LinkTransitions.getIdFromLink(message.getText()));
    stateMachine.getDataManager().setObject(DataKeys.TEA_TYPE, teaType);
    stateMachine.setState(CreateTeaTypeState.class);
    getPageManager().sendPageMessage(EditTeaTypePage.class, message, stateMachine);
  }

  @Override
  public void catchIdDelete(Message message, StateMachine stateMachine) {
    TeaType teaType = teaTypeService.getTeaTypeById(LinkTransitions.getIdFromLink(message.getText()));
    if (teaTypeService.isAllowedToDelete(teaType)) {
      stateMachine.getDataManager().setObject(DataKeys.TEA_TYPE, teaType);
      stateMachine.setState(DeleteTeaTypeState.class);
      getPageManager().sendPageMessage(DeleteWithNameInputPage.class, message, stateMachine);
    } else {
      getPageManager().sendPageMessage(DeleteTeaTypeHasCichlidsPage.class, message, stateMachine);
      stateMachine.getDialogHistory().goToCurrentState(stateMachine);
    }
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
