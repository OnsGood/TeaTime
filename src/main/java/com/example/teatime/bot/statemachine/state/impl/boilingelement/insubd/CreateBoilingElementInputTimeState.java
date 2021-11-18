package com.example.teatime.bot.statemachine.state.impl.boilingelement.insubd;

import com.example.teatime.bd.entity.BoilingElement;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.MessageException;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.history.Historical;
import com.example.teatime.bot.statemachine.page.impl.MainPage;
import com.example.teatime.bot.statemachine.page.impl.boilingelement.insubd.CreateBoilingElementPage;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.state.impl.AbstractState;
import com.example.teatime.bot.statemachine.state.impl.MainPageState;
import com.example.teatime.bot.statemachine.state.impl.boiling.insubd.CreateBoilingState;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.BOILING;
import static com.example.teatime.bot.statemachine.datamanager.api.DataKeys.BOILING_ELEMENT;

@Component("CreateBoilingElementInputTimeState")
public class CreateBoilingElementInputTimeState extends AbstractState implements State {
  @Override
  @Historical
  public void mainPage(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(MainPageState.class);
    getPageManager().sendPageMessage(MainPage.class, message, stateMachine);
  }

  @Override
  public void insupd(MessageDto message, StateMachine stateMachine) {
    stateMachine.setState(CreateBoilingState.class);
    getPageManager().sendPageMessage(CreateBoilingElementPage.class, message, stateMachine);
  }

  @Override
  public void unknownMessage(MessageDto message, StateMachine stateMachine) {
    BoilingElement boiling = stateMachine.getDataManager().getObject(BOILING_ELEMENT, BoilingElement.class);

    try {
      boiling.setSeconds(Long.parseLong(message.getText()));
    } catch (Exception e) {
      throw new MessageException("Ошибка преобразования сообщения в число секунд");
    }

    stateMachine.setState(CreateBoilingElementState.class);
    getPageManager().sendPageMessage(CreateBoilingElementPage.class, message, stateMachine);
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