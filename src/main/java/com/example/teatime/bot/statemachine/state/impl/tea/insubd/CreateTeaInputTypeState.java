package com.example.teatime.bot.statemachine.state.impl.tea.insubd;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.CreateTeaPage;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.EditTeaPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaService;
import com.example.teatime.service.api.TeaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CreateTeaInputTypeState extends AbstractState {
  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

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
  public void insupdTea(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(CreateTeaState.class));
    boolean teaExist = teaService.exist(stateMachine.getDataManager().getObject(DataKeys.MODIFIED_TEA, Tea.class));
    MessageTools.sendMessage(getPageManager().getPage(teaExist ? EditTeaPage.class : CreateTeaPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void catchTeaTypeId(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(CreateTeaState.class));
    Tea tea = stateMachine.getDataManager().getObject(DataKeys.MODIFIED_TEA, Tea.class);
    TeaType teaType = teaTypeService.getTeaTypeById(LinkTransitions.getIdFromLink(message.getText()));
    tea.setTeaType(teaType);
    boolean teaExist = teaService.exist(stateMachine.getDataManager().getObject(DataKeys.MODIFIED_TEA, Tea.class));
    MessageTools.sendMessage(getPageManager().getPage(teaExist ? EditTeaPage.class : CreateTeaPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }


  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
