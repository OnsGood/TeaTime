package com.example.teatime.bot.statemachine.page.impl.teatypelist;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.teatime.bot.statemachine.MessageTools.getSendMessage;
import static com.example.teatime.bot.statemachine.MessageTools.setKeyboard;

@Component
public class TeaTypeListPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.CREATE_TEA_TYPE.getTitle()},
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

    Iterable<TeaType> teas = teaTypeService.listTeaType();

    if (teas.iterator().hasNext()) {
      builder.append("Виды чаев: ")
          .append("\n")
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
        "Перейти - " + LinkTransitions.TEA_TYPE.getPrefix() + teaType.getId() + "\n";
  }
}
