package com.example.teatime.bot.statemachine.state.impl.teatype.insubd;

import java.util.Set;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.state.api.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.InputParamPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.TeaTypeValidationBadPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.insubd.CreateTeaTypeSuccesPage;
import com.example.teatime.bot.statemachine.page.impl.teatype.insubd.EditTeaTypeSuccesPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.TeaTypeService;
import com.example.teatime.service.api.ValidateResult;

@Component("CreateTeaTypeState")
public class CreateTeaTypeState extends AbstractState implements State {
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
    TeaType teaType = stateMachine.getDataManager().getObject(DataKeys.TEA_TYPE, TeaType.class);
    teaType.setActive(true);

    ValidateResult validateResult = teaTypeService.validateWithMessage(teaType, stateMachine.isUserModerator());
    boolean teaExist = teaTypeService.exist(teaType);

    if (validateResult.isAllGood()) {
      teaTypeService.save(teaType);
      stateMachine.setState(MainPageState.class);

      sendPageMessage(teaExist ? EditTeaTypeSuccesPage.class : CreateTeaTypeSuccesPage.class, message, stateMachine);
      sendPageMessage(MainPage.class, message, stateMachine);
    } else {
      sendPageMessage(TeaTypeValidationBadPage.class, message, stateMachine);
    }
  }

  @Override
  public void setTitle(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(InputTeaTypeNameState.class);
    sendPageMessage(InputParamPage.class, message, stateMachine);
  }

  @Override
  public void setDescr(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(InputTeaTypeDescrState.class);
    sendPageMessage(InputParamPage.class, message, stateMachine);
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
