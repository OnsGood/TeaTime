package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.teatime.bot.statemachine.MessageTools.getSendMessage;
import static com.example.teatime.bot.statemachine.MessageTools.setKeyboard;

@Component
public class TeaListFromNamePage implements Page {
  private static final String[][] keyboard = new String[][]{
      {KeyTransitions.MAIN_PAGE.getTitle()},
      {KeyTransitions.CREATE_TEA.getTitle()},
      {KeyTransitions.TEA_NAME_SEARCH.getTitle()},
  };

  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = getSendMessage(receivedMessage);
    setKeyboard(keyboard, sendMessage);
    StringBuilder builder = new StringBuilder();

    Iterable<Tea> teas = teaService.listTeaByName(receivedMessage.getText());

    if (teas.iterator().hasNext()) {
      builder.append("Найдены чаи : ")
          .append("\n")
          .append("\n");

      teas.forEach(tea -> builder
          .append(formStringFromTea(tea))
          .append("\n")
      );
    } else {
      builder.append("Совпадения не найдены");
    }

    sendMessage.setText(builder.toString());
    return sendMessage;
  }

  private String formStringFromTea(Tea tea) {
    return tea.getTitle() + "\n" +
        "Вид - " + tea.getTeaType().getTitle() + "\n" +
        "Описание - " + tea.getDescription() + "\n" +
        "Перейти к заваркам: " + LinkTransitions.GET_TEA.getPrefix() + tea.getId() + "\n";
  }
}
