package com.example.teatime.bot.statemachine.page.impl.tea.list;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.GoListFacade;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public abstract class AbstractTeaListPage implements Page {
  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    Iterable<Tea> teas = listTeas(receivedMessage.text());
    return new PageMessageBuilder(receivedMessage)
      .keyboard(getKeyboard(stateMachine))
      .part(new GoListFacade<Tea>()
        .setHead("Чаи:")
        .setList(teas)
        .setToIdFunction(Tea::getId)
        .setToTextFunction(this::formStringFromTea)
        .setNoFoundMessage(emptyListMessage())
      )
      .buildMessageList();
  }

  protected abstract Iterable<Tea> listTeas(String text);

  protected abstract String[][] getKeyboard(StateMachine stateMachine);

  protected abstract String formStringFromTea(Tea tea);

  protected abstract String emptyListMessage();
}
