package com.example.teatime.bot.statemachine.state.impl.createtea;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.InputParamPage;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.createtea.CreateTeaSuccesPage;
import com.example.teatime.bot.statemachine.page.impl.createtea.CreateTeaTeaTypeListPage;
import com.example.teatime.bot.statemachine.page.impl.createtea.CreateTeaValidationBadPage;
import com.example.teatime.service.api.ValidateResult;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.teatypelist.MainPageState;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CreateTeaState extends AbstractState {
  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(MainPageState.class));
    MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void createTea(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    Tea tea = stateMachine.getDataManager().getObject(DataKeys.CREATED_TEA, Tea.class);
    tea.setActive(true);
    ValidateResult validateResult = teaService.validateTeaWithMessage(tea);

    if (validateResult.isAllGood()) {
      teaService.save(tea);
      stateMachine.setState(getStateManager().getState(MainPageState.class));
      MessageTools.sendMessage(getPageManager().getPage(CreateTeaSuccesPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
      MessageTools.sendMessage(getPageManager().getPage(MainPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
    } else {
      MessageTools.sendMessage(getPageManager().getPage(CreateTeaValidationBadPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
    }


  }

  @Override
  public void setTitle(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(InputTeaNameState.class));
    MessageTools.sendMessage(getPageManager().getPage(InputParamPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void setDescr(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(InputTeaDescrState.class));
    MessageTools.sendMessage(getPageManager().getPage(InputParamPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void setType(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    stateMachine.setState(getStateManager().getState(InputTeaTypeState.class));
    MessageTools.sendMessage(getPageManager().getPage(CreateTeaTeaTypeListPage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
