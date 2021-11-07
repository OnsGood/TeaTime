package com.example.teatime.bot.statemachine.state.impl.teatype.insubd;

import java.util.Set;

import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.state.api.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

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
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(Message message, StateMachine stateMachine) {
    TeaType teaType = stateMachine.getDataManager().getObject(DataKeys.TEA_TYPE, TeaType.class);
    teaType.setActive(true);

    ValidateResult validateResult = teaTypeService.validateWithMessage(teaType);
    boolean teaExist = teaTypeService.exist(teaType);

    if (validateResult.isAllGood()) {
      teaTypeService.save(teaType);
      stateMachine.setState(MainPageState.class);

      getPageManager().sendPageMessage(teaExist ? EditTeaTypeSuccesPage.class : CreateTeaTypeSuccesPage.class, message, stateMachine);
      getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
    } else {
      getPageManager().sendPageMessage(TeaTypeValidationBadPage.class, message, stateMachine);
    }
  }

  @Override
  public void setTitle(Message message, StateMachine stateMachine) {
    stateMachine.setState(InputTeaTypeNameState.class);
    getPageManager().sendPageMessage(InputParamPage.class, message, stateMachine);
  }

  @Override
  public void setDescr(Message message, StateMachine stateMachine) {
    stateMachine.setState(InputTeaTypeDescrState.class);
    getPageManager().sendPageMessage(InputParamPage.class, message, stateMachine);
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
