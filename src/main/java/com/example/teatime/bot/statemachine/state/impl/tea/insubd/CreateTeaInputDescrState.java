package com.example.teatime.bot.statemachine.state.impl.tea.insubd;

import java.util.Set;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.CreateTeaPage;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.EditTeaPage;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CreateTeaInputDescrState extends AbstractState {
  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  public void mainPage(Message message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupdTea(Message message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaState.class);
    boolean teaExist = teaService.exist(stateMachine.getDataManager().getObject(DataKeys.MODIFIED_TEA, Tea.class));
    getPageManager().sendPageMessage(teaExist ? EditTeaPage.class : CreateTeaPage.class, message, stateMachine);
  }

  @Override
  public void unknownMessage(Message message, StateMachine stateMachine) {
    stateMachine.setState(CreateTeaState.class);
    Tea tea = stateMachine.getDataManager().getObject(DataKeys.MODIFIED_TEA, Tea.class);
    tea.setDescription(message.getText());
    boolean teaExist = teaService.exist(stateMachine.getDataManager().getObject(DataKeys.MODIFIED_TEA, Tea.class));
    getPageManager().sendPageMessage(teaExist ? EditTeaPage.class : CreateTeaPage.class, message, stateMachine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(DataKeys.MODIFIED_TEA);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
