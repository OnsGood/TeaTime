package com.example.teatime.bot.statemachine.page.impl.tea.insubd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaTypeService;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class TeaChooseTeaTypeListPage implements Page {
  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
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

    return List.of(sendMessage);
  }

  private String formStringFromTeaType(TeaType teaType) {
    return teaType.getTitle() + "\n" +
      "Описание - " + teaType.getDescription() + "\n" +
      "Выбрать: " + LinkTransitions.GO.getPrefix() + teaType.getId() + "\n";
  }
}
