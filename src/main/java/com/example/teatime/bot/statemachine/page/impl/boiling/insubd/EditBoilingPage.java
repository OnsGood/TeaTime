package com.example.teatime.bot.statemachine.page.impl.boiling.insubd;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class EditBoilingPage implements Page {
  private static final String[][] keyboard = new String[][]{
      {KeyTransitions.SAVE.getTitle()},
      {KeyTransitions.CHANGE_TITLE.getTitle()},
      {KeyTransitions.CHANGE_DESCR.getTitle()},
      {KeyTransitions.BACK.getTitle()},
      {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    setKeyboard(keyboard, sendMessage);
    StringBuilder builder = new StringBuilder();
    Tea tea = stateMachine.getDataManager().getObject(DataKeys.TEA, Tea.class);

    builder
        .append("Редактирование способа заварки - '").append(tea.getTitle()).append("' ").append("вида '").append(tea.getTeaType().getTitle()).append("'").append("\n")
        .append("\n")
        .append(tea.getDescription()).append("\n")
        .append("\n");

    sendMessage.setText(builder.toString());
    return sendMessage;
  }
}
