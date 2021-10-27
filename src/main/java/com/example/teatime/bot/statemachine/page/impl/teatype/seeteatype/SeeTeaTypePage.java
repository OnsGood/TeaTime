package com.example.teatime.bot.statemachine.page.impl.teatype.seeteatype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaTypeService;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class SeeTeaTypePage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = getSendMessage(receivedMessage);
    setKeyboard(keyboard, sendMessage);
    StringBuilder builder = new StringBuilder();
    long teaTypeId = MessageTools.getIdFromMessage(receivedMessage.getText());

    TeaType teatype = teaTypeService.getTeaTypeById(teaTypeId);

    builder
      .append("Вид чая '").append(teatype.getTitle()).append("' ").append("\n")
      .append("\n")
      .append(teatype.getDescription()).append("\n")
      .append("\n")
      .append("Перейти к чаям - ").append(LinkTransitions.TEA_TYPE.makeLink(teaTypeId)).append("\n")
      .append("Редактировать - ").append(LinkTransitions.EDIT.makeLink(teaTypeId)).append("\n")
      .append("Удалить - ").append(LinkTransitions.DELETE.makeLink(teaTypeId)).append("\n");


    sendMessage.setText(builder.toString());
    return sendMessage;
  }
}
