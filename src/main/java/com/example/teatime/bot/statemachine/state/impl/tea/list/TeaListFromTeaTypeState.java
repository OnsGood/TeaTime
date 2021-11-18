package com.example.teatime.bot.statemachine.state.impl.tea.list;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.CreateTeaPage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.tea.insubd.CreateTeaState;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.TEA;
import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.TEA_TYPE;

@Component("TeaListFromTeaTypeState")
public class TeaListFromTeaTypeState extends AbstractTeaListState implements State {

  @Override
  public void insupd(MessageDto message, StateMachine stateMachine) {
    Tea tea = new Tea();
    TeaType parentTeaType = stateMachine.getDataManager().getObject(TEA_TYPE, TeaType.class);
    tea.setTeaType(parentTeaType);
    stateMachine.getDataManager().setObject(TEA, tea);
    stateMachine.setState(CreateTeaState.class);
    getPageManager().sendPageMessage(CreateTeaPage.class, message, stateMachine);
  }

  @Override
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    super.mainPage(message, stateMachine);
  }

  @Override
  @Historical
  public void catchIdGo(MessageDto message, StateMachine stateMachine) {
    super.catchIdGo(message, stateMachine);
  }

  @Override
  public Set<DataKeys> getSupportedData() {
    return Set.of(TEA, TEA_TYPE);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
