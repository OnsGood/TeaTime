package com.example.teatime.bot.statemachine.state.impl.tea.delete;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DeleteTeaState extends AbstractState {
  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  public void unknownMessage(Message message, StateMachine machine) {
    logState(message, this.getClass());
    Tea tea = machine.getDataManager().getObject(DataKeys.MODIFIED_TEA, Tea.class);
    if (tea.getTitle().equals(message.getText())) {
      teaService.delete(tea);
    }
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
