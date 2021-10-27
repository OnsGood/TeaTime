package com.example.teatime.bot.statemachine.page.impl.tea.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaTypeService;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class CreateTeaTeaTypeListPage implements Page {
  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = getSendMessage(receivedMessage);
    MessageTools.removeSpecialKeyboard(sendMessage);

    StringBuilder builder = new StringBuilder();

    Iterable<TeaType> teas = teaTypeService.listTeaType();

    if (teas.iterator().hasNext()) {
      builder
        .append("Выберите вид чая из нижеперечисленных:").append("\n")
        .append("\n");

      teas.forEach(teaType -> builder
        .append(formStringFromTeaType(teaType))
        .append("\n")
      );
    } else {
      builder.append("Виды чаев не найдены");
    }

    sendMessage.setText(builder.toString());

    return sendMessage;
  }

  private String formStringFromTeaType(TeaType teaType) {
    return teaType.getTitle() + "\n" +
      "Описание - " + teaType.getDescription() + "\n" +
      "Выбрать: " + LinkTransitions.TEA_TYPE.getPrefix() + teaType.getId() + "\n";
  }
}
