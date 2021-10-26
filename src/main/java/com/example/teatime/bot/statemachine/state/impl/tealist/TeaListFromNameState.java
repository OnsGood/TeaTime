package com.example.teatime.bot.statemachine.state.impl.tealist;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.impl.InputTeaNamePage;
import com.example.teatime.bot.statemachine.page.impl.tealist.TeaListFromNamePage;

@Component
public class TeaListFromNameState extends AbstractTeaListState {

  @Override
  public void back(Message message, StateMachine stateMachine) {
    mainPage(message, stateMachine);
  }

  @Override
  public void unknownMessage(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    MessageTools.sendMessage(getPageManager().getPage(TeaListFromNamePage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  public void listTeaFromName(Message message, StateMachine stateMachine) {
    logState(message, this.getClass());
    MessageTools.sendMessage(getPageManager().getPage(InputTeaNamePage.class).getPageMessage(message, stateMachine), stateMachine.getPollingBot());
  }

  @Override
  protected Tea getTea() {
    return new Tea();
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
