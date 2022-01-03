package com.example.teatime.bot.statemachine.state.impl.boiling.insubd;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.InputParamPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.BoilingValidationBadPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.insubd.CreateBoilingSuccesPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.insubd.EditBoilingSuccesPage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.BoilingService;
import com.example.teatime.service.api.ValidateResult;

import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.*;

@Component("CreateBoilingState")
public class CreateBoilingState extends AbstractState implements State {
  private BoilingService boilingService;

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Override
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(MessageDto message, StateMachine stateMachine) {
    Boiling boiling = stateMachine.getDataManager().getObject(BOILING, Boiling.class);
    boiling.setActive(true);

    ValidateResult validateResult = boilingService.validateWithMessage(boiling, stateMachine.isUserModerator());

    if (validateResult.isAllGood()) {
      boolean boilingExist = boilingService.exist(boiling);

      boilingService.save(boiling);

      sendPageMessage(boilingExist ? EditBoilingSuccesPage.class : CreateBoilingSuccesPage.class, message, stateMachine);
      stateMachine.getDialogHistory().goToCurrentState(stateMachine);
    } else {
      sendPageMessage(BoilingValidationBadPage.class, message, stateMachine);
    }
  }

  @Override
  public void setTitle(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateBoilingInputNameState.class);
    sendPageMessage(InputParamPage.class, message, stateMachine);
  }

  @Override
  public void setDescr(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateBoilingInputDescrState.class);
    sendPageMessage(InputParamPage.class, message, stateMachine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(BOILING, TEA);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
