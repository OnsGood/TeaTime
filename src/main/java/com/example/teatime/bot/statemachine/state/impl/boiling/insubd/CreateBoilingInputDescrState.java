package com.example.teatime.bot.statemachine.state.impl.boiling.insubd;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.insubd.CreateBoilingPage;
import com.example.teatime.bot.statemachine.page.impl.boiling.insubd.EditBoilingPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.BoilingService;

@Component
public class CreateBoilingInputDescrState extends AbstractState {
  private BoilingService boilingService;

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(Message message, StateMachine stateMachine) {
    stateMachine.setState(CreateBoilingState.class);
    boolean exist = boilingService.exist(stateMachine.getDataManager().getObject(DataKeys.BOILING, Boiling.class));
    getPageManager().sendPageMessage(exist ? EditBoilingPage.class : CreateBoilingPage.class, message, stateMachine);
  }

  @Override
  public void unknownMessage(Message message, StateMachine stateMachine) {
    stateMachine.setState(CreateBoilingState.class);
    Boiling boiling = stateMachine.getDataManager().getObject(DataKeys.BOILING, Boiling.class);
    boiling.setDescription(message.getText());
    boolean teaExist = boilingService.exist(stateMachine.getDataManager().getObject(DataKeys.BOILING, Boiling.class));
    getPageManager().sendPageMessage(teaExist ? EditBoilingPage.class : CreateBoilingPage.class, message, stateMachine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(DataKeys.BOILING);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
