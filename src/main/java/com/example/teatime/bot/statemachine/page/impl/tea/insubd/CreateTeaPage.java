package com.example.teatime.bot.statemachine.page.impl.tea.insubd;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

import static java.util.Optional.ofNullable;

@Component
public class CreateTeaPage implements Page {
  private static final String[][] keyboard = new String[][]{
      {KeyTransitions.SAVE.getTitle()},
      {KeyTransitions.SET_TITLE.getTitle()},
      {KeyTransitions.SET_DESCR.getTitle()},
      {KeyTransitions.SET_TYPE.getTitle()},
      {KeyTransitions.BACK.getTitle()},
      {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = MessageTools.makeSendMessage(receivedMessage);
    MessageTools.setKeyboard(keyboard, sendMessage);

    Tea tea = stateMachine.getDataManager().getObject(DataKeys.MODIFIED_TEA, Tea.class);

    String text = "Создание нового чая." + "\n"
        + "Для создания необходимо заполнить все поля, и повторно нажать на клавишу '" + KeyTransitions.CREATE_TEA.getTitle() + "'.\n"
        + "Для заполнения поля, найдите соответствующую клавишу на клавиатуре и нажмите ее. " + "\n" + "\n"
        + "Новый чай: " + "\n";


    if (Objects.nonNull(tea)) {
      String teaTypeTitle = ofNullable(tea.getTeaType())
          .map(TeaType::getTitle)
          .orElse("");

      text = text +
          "Название - " + ofNullable(tea.getTitle()).orElse("") + "\n" +
          "Описание - " + ofNullable(tea.getDescription()).orElse("") + "\n" +
          "Вид - " + teaTypeTitle + "\n";
    }

    sendMessage.setText(text);
    return sendMessage;
  }
}
