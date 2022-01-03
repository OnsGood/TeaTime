package com.example.teatime.bot.statemachine.state.impl.tea.insubd;

import java.util.Set;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.state.api.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.entity.TeaType;
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

@Component("CreateTeaInputTypeState")
public class CreateTeaInputTypeState extends AbstractState implements State {
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
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaState.class);
    boolean teaExist = teaService.exist(stateMachine.getDataManager().getObject(DataKeys.TEA, Tea.class));
    sendPageMessage(teaExist ? EditTeaPage.class : CreateTeaPage.class, message, stateMachine);
  }

  @Override
  public void catchIdGo(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaState.class);
    Tea tea = stateMachine.getDataManager().getObject(DataKeys.TEA, Tea.class);
    TeaType teaType = teaTypeService.getTeaTypeById(LinkTransitions.getIdFromLink(message.text()));
    tea.setTeaType(teaType);
    boolean teaExist = teaService.exist(stateMachine.getDataManager().getObject(DataKeys.TEA, Tea.class));
    sendPageMessage(teaExist ? EditTeaPage.class : CreateTeaPage.class, message, stateMachine);
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
