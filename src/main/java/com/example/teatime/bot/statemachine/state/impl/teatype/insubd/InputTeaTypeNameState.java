package com.example.teatime.bot.statemachine.state.impl.teatype.insubd;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.insubd.CreateTeaTypePage;
import com.example.teatime.bot.statemachine.page.impl.teatype.insubd.EditTeaTypePage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.TeaTypeService;

@Component("InputTeaTypeNameState")
public class InputTeaTypeNameState extends AbstractState implements State {

  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }


  @Override
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaTypeState.class);
    boolean exist = teaTypeService.exist(stateMachine.getDataManager().getObject(DataKeys.TEA_TYPE, TeaType.class));
    getPageManager().sendPageMessage(exist ? EditTeaTypePage.class : CreateTeaTypePage.class, message, stateMachine);
  }


  @Override
  public void unknownMessage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaTypeState.class);
    TeaType teaType = stateMachine.getDataManager().getObject(DataKeys.TEA_TYPE, TeaType.class);
    teaType.setTitle(message.getText());
    boolean exist = teaTypeService.exist(stateMachine.getDataManager().getObject(DataKeys.TEA_TYPE, TeaType.class));
    getPageManager().sendPageMessage(exist ? EditTeaTypePage.class : CreateTeaTypePage.class, message, stateMachine);
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
