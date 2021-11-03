package com.example.teatime.bot.statemachine.state.impl.tea.list;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.impl.tea.insubd.CreateTeaPage;
import com.example.teatime.bot.statemachine.state.impl.tea.insubd.CreateTeaState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class TeaListFromTeaTypeState extends AbstractTeaListState {

  @Override
  public void insupd(Message message, StateMachine stateMachine) {
    Tea tea = new Tea();
    stateMachine.getDataManager().setObject(DataKeys.TEA, tea);
    stateMachine.setState(CreateTeaState.class);
    getPageManager().sendPageMessage(CreateTeaPage.class, message, stateMachine);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
