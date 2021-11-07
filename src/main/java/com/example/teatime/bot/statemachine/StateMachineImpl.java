package com.example.teatime.bot.statemachine;

import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.datamanager.api.DataManager;
import com.example.teatime.bot.statemachine.datamanager.impl.DataManagerImpl;
import com.example.teatime.bot.statemachine.history.DialogHistory;
import com.example.teatime.bot.statemachine.identifier.MessageIdentifier;
import com.example.teatime.bot.statemachine.page.impl.ErrorPage;
import com.example.teatime.bot.statemachine.pagemanager.api.PageManager;
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
public class StateMachineImpl implements StateMachine {
  private static final Logger log = Logger.getLogger(StateMachineImpl.class);

  private TelegramLongPollingBot pollingBot;
  private StateManager stateManager;
  private PageManager pageManager;
  private State state;
  private MessageIdentifier messageIdentifier;
  private final DataManager dataManager;
  private final DialogHistory dialogHistory;

  @InjectStateIdentifiersByMarkedMethods
  public void setMessageIdentifier(MessageIdentifier messageIdentifier) {
    this.messageIdentifier = messageIdentifier;
  }

  @Autowired
  public void setStateManager(StateManager stateManager) {
    this.stateManager = stateManager;
  }

  @Autowired
  public void setPageManager(PageManager pageManager) {
    this.pageManager = pageManager;
  }

  public StateMachineImpl() {
    log.info("started new StateMachine");
    dataManager = new DataManagerImpl();
    dialogHistory = new DialogHistory(30);
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
    state = stateManager.getState(stateManager.getDefaultStateClass());
    setIdentifiers();
  }

  @Override
  public void setBot(TelegramLongPollingBot pollingBot) {
    this.pollingBot = pollingBot;
  }

  @Override
  public TelegramLongPollingBot getBot() {
    return pollingBot;
  }

  @Override
  public void setState(Class<? extends State> stateClass) {
    State stateToSwitch = stateManager.getState(stateClass);
    log.info("change state from " + this.state + " to " + stateToSwitch);
    this.state = stateToSwitch;
  }

  @Override
  public State getState() {
    return state;
  }

  @Override
  public void resolveMessage(Message message) {
    log.info("message resolved by state " + state.getClass().getSimpleName());
    try {
      dataManager.updateData(state.getSupportedData());
      messageIdentifier.identifyMessage(message);
    } catch (Exception e) {
      dataManager.setObject(DataKeys.ERROR, e);
      log.error("resolving message error. turn to main page", e);
      setState(stateManager.getDefaultStateClass());
      pageManager.sendPageMessage(ErrorPage.class, message, this);
      state.mainPage(message, this);
    }
  }

  @Override
  public void resolvePrevMessage() {
    dialogHistory.goToPrevState(this);
  }

  @Override
  public DialogHistory getDialogHistory() {
    return dialogHistory;
  }

  @Override
  public DataManager getDataManager() {
    return dataManager;
  }

  @Override
  public StateManager getStateManager() {
    return stateManager;
  }

}
