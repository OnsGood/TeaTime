package com.example.teatime.bot.statemachine.page.impl.tea.list;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;

import static com.example.teatime.bot.statemachine.MessageTools.*;

public abstract class AbstractTeaListPage implements Page {
  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    setKeyboard(getKeyboard(), sendMessage);
    StringBuilder builder = new StringBuilder();

    Iterable<Tea> teas = listTeas(receivedMessage.getText());

    if (teas.iterator().hasNext()) {
      builder.append("Найдены чаи : ")
        .append("\n")
        .append("\n");

      teas.forEach(tea -> builder
        .append(formStringFromTea(tea))
        .append("\n")
      );
    } else {
      builder.append("Список чаев пуст. Заполните его.");
    }

    sendMessage.setText(builder.toString());
    return sendMessage;
  }

  protected abstract Iterable<Tea> listTeas(String text);

  protected abstract String[][] getKeyboard();

  protected abstract String formStringFromTea(Tea tea);
}
