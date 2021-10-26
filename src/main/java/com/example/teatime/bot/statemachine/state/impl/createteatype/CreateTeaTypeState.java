package com.example.teatime.bot.statemachine.state.impl.createteatype;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.InputParamPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.createteatype.CreateTeaTypeSuccesPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.teatypelist.MainPageState;
import com.example.teatime.service.api.TeaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CreateTeaTypeState extends AbstractState {
  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }


  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void createTeaType(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    TeaType teaType = stateMachine.getDataManager().getObject(DataKeys.CREATED_TEA_TYPE, TeaType.class);
    teaType.setActive(true);
    teaTypeService.save(teaType);
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(CreateTeaTypeSuccesPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void setTitle(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(InputTeaTypeNameState.class));
    MessageTools.sendMessage(getPageManager().getPage(InputParamPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void setDescr(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(InputTeaTypeDescrState.class));
    MessageTools.sendMessage(getPageManager().getPage(InputParamPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }


  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
