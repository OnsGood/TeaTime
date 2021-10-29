package com.example.teatime.bot.statemachine.page.impl.tea.see;

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

import static com.example.teatime.bot.statemachine.MessageTools.makeSendMessage;
import static com.example.teatime.bot.statemachine.MessageTools.setKeyboard;

@Component
public class SeeTeaPage implements Page {
  private static final String[][] keyboard = new String[][]{
      {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    setKeyboard(keyboard, sendMessage);
    StringBuilder builder = new StringBuilder();
    long teaId = LinkTransitions.getIdFromLink(receivedMessage.getText());

    Tea tea = teaService.getTeaById(teaId);

    builder
        .append("Чай '").append(tea.getTitle()).append("' ").append("вида '").append(tea.getTeaType().getTitle()).append("'").append("\n")
        .append("\n")
        .append(tea.getDescription()).append("\n")
        .append("\n")
        .append("Перейти к заваркам - ").append(LinkTransitions.TEA.makeLink(teaId)).append("\n")
        .append("Редактировать - ").append(LinkTransitions.EDIT.makeLink(teaId)).append("\n")
        .append("Удалить - ").append(LinkTransitions.DELETE.makeLink(teaId)).append("\n");


    sendMessage.setText(builder.toString());
    return sendMessage;
  }
}