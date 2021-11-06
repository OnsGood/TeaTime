package com.example.teatime.bot.statemachine.state.impl.boilingelement.insubd;

import com.example.teatime.bd.entity.BoilingElement;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.InputParamPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.see.SeeBoilingFromDataManagerPage;
import com.example.teatime.bot.statemachine.page.impl.boilingelement.BoilingElementValidationBadPage;
import com.example.teatime.bot.statemachine.page.impl.boilingelement.insubd.CreateBoilingElementSuccesPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.see.SeeBoilingState;
import com.example.teatime.service.api.BoilingElementService;
import com.example.teatime.service.api.ValidateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Set;

import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.BOILING;
import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.BOILING_ELEMENT;

@Component
public class CreateBoilingElementState extends AbstractState {
  private BoilingElementService boilingElementService;

  @Autowired
  public void setBoilingElementService(BoilingElementService boilingElementService) {
    this.boilingElementService = boilingElementService;
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(Message message, StateMachine stateMachine) {
    BoilingElement boilingElement = stateMachine.getDataManager().getObject(BOILING_ELEMENT, BoilingElement.class);
    boilingElement.setActive(true);

    ValidateResult validateResult = boilingElementService.validateWithMessage(boilingElement);

    if (validateResult.isAllGood()) {
      boilingElementService.save(boilingElement);
      stateMachine.setState(SeeBoilingState.class);

      getPageManager().sendPageMessage(CreateBoilingElementSuccesPage.class, message, stateMachine);
      getPageManager().sendPageMessage(SeeBoilingFromDataManagerPage.class, message, stateMachine);
    } else {
      getPageManager().sendPageMessage(BoilingElementValidationBadPage.class, message, stateMachine);
    }
  }

  @Override
  public void setTime(Message message, StateMachine stateMachine) {
    stateMachine.setState(CreateBoilingElementInputTimeState.class);
    getPageManager().sendPageMessage(InputParamPage.class, message, stateMachine);
  }

  @Override
  public void setTemp(Message message, StateMachine stateMachine) {
    stateMachine.setState(CreateBoilingElementInputTempState.class);
    getPageManager().sendPageMessage(InputParamPage.class, message, stateMachine);
  }

  @Override
  public void setMass(Message message, StateMachine stateMachine) {
    stateMachine.setState(CreateBoilingElementInputMassState.class);
    getPageManager().sendPageMessage(InputParamPage.class, message, stateMachine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(BOILING_ELEMENT, BOILING);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
