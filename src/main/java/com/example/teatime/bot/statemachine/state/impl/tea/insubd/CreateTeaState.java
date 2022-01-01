package com.example.teatime.bot.statemachine.state.impl.tea.insubd;

import java.util.Set;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.state.api.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.InputParamPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.TeaValidationBadPage;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.CreateTeaSuccesPage;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.EditTeaSuccesPage;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.TeaChooseTeaTypeListPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.TeaService;
import com.example.teatime.service.api.ValidateResult;

@Component("CreateTeaState")
public class CreateTeaState extends AbstractState implements State {
  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(MessageDto message, StateMachine stateMachine) {
    Tea tea = stateMachine.getDataManager().getObject(DataKeys.TEA, Tea.class);
    boolean teaExist = teaService.exist(tea);
    tea.setActive(true);
    ValidateResult validateResult = teaService.validateTeaWithMessage(tea, stateMachine.isUserModerator());

    if (validateResult.isAllGood()) {
      teaService.save(tea);

      getPageManager().sendPageMessage(teaExist ? EditTeaSuccesPage.class : CreateTeaSuccesPage.class, message, stateMachine);
      stateMachine.getDialogHistory().goToCurrentState(stateMachine);
    } else {
      getPageManager().sendPageMessage(TeaValidationBadPage.class, message, stateMachine);
    }
  }

  @Override
  public void setTitle(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaInputNameState.class);
    getPageManager().sendPageMessage(InputParamPage.class, message, stateMachine);
  }

  @Override
  public void setDescr(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaInputDescrState.class);
    getPageManager().sendPageMessage(InputParamPage.class, message, stateMachine);
  }

  @Override
  public void setType(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaInputTypeState.class);
    getPageManager().sendPageMessage(TeaChooseTeaTypeListPage.class, message, stateMachine);
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
