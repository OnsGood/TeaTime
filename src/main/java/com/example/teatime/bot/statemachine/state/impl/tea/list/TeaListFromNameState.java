package com.example.teatime.bot.statemachine.state.impl.tea.list;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.tea.list.InputTeaNamePage;
import com.example.teatime.bot.statemachine.page.impl.tea.list.TeaListFromNamePage;
import com.example.teatime.bot.statemachine.page.impl.tea.see.SeeTeaPage;

@Component
public class TeaListFromNameState extends AbstractTeaListState {

  @Override
  public void unknownMessage(Message message, StateMachine stateMachine) {
    getPageManager().sendPageMessage(TeaListFromNamePage.class, message, stateMachine);
  }

  @Override
  public void listTeaFromName(Message message, StateMachine stateMachine) {
    getPageManager().sendPageMessage(InputTeaNamePage.class, message, stateMachine);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
