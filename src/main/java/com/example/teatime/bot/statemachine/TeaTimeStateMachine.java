package com.example.teatime.bot.statemachine;

import com.example.teatime.bot.statemachine.datamanager.api.DataManager;
import com.example.teatime.bot.statemachine.datamanager.impl.DataManagerImpl;
import com.example.teatime.bot.statemachine.identifier.MessageIdentifier;
import com.example.teatime.bot.statemachine.state.api.State;
import com.example.teatime.bot.statemachine.statemanager.api.StateManager;
import com.example.teatime.bot.statemachine.transition.InjectStateIdentifiersByMarkedMethods;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TeaTimeStateMachine implements StateMachine {
  private static final Logger log = Logger.getLogger(TeaTimeStateMachine.class);

  private TelegramLongPollingBot pollingBot;
  private StateManager stateManager;
  private State state;
  private MessageIdentifier messageIdentifier;
  private final DataManager dataManager;

  @InjectStateIdentifiersByMarkedMethods
  public void setMessageIdentifier(MessageIdentifier messageIdentifier) {
    this.messageIdentifier = messageIdentifier;
  }

  @Autowired
  public void setStateManager(StateManager stateManager) {
    this.stateManager = stateManager;
  }

  public TeaTimeStateMachine() {
    log.info("started new TeaTimeStateMachine");
    dataManager = new DataManagerImpl();
  }

  /**
   * Установка дополнительных кастомных идентифаеров.
   */
  private void setIdentifiers() {
    messageIdentifier
        .addIdentifier(
            message -> true,
            message -> state.unknownMessage(message, this));
  }

  @PostConstruct
  public void postConstruct() {
    state = stateManager.getDefaultState();
    setIdentifiers();
  }

  @Override
  public void setPollingBot(TelegramLongPollingBot pollingBot) {
    this.pollingBot = pollingBot;
  }

  @Override
  public TelegramLongPollingBot getPollingBot() {
    return pollingBot;
  }

  @Override
  public void setState(State state) {
    log.info("change state from " + this.state + " to " + state);
    this.state = state;
  }

  @Override
  public State getState() {
    return state;
  }

  @Override
  public void resolveMessage(Message message) {
    log.info("message resolved by state " + state.getClass().getSimpleName());
    try {
      messageIdentifier.identifyMessage(message);
    } catch (Exception e) {
      log.error("Ошибка обработки сообщения", e);
    }
  }

  @Override
  public DataManager getDataManager() {
    return dataManager;
  }

}
