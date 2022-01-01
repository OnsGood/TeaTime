package com.example.teatime.bot.statemachine.page.impl.teatype.list;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class TeaTypeListPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private static final String[][] moderKeyboard = new String[][]{
    {KeyTransitions.CREATE_TEA_TYPE.getTitle()},
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };


  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    setKeyboard(keyboard, moderKeyboard, sendMessage, stateMachine.isUserModerator());

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

    return List.of(sendMessage);
  }

  @Override
  public EditMessageText getCallbackResponse(MessageDto callbackMessage, StateMachine stateMachine) {
    EditMessageText sendMessage = makeCallbackMessage(callbackMessage);

    sendMessage.setText(callbackMessage.text());

    return sendMessage;
  }

  private String formStringFromTeaType(TeaType teaType) {
    return teaType.getTitle() + "\n" +
      "Перейти - " + LinkTransitions.GO.getPrefix() + teaType.getId() + "\n";
  }
}
