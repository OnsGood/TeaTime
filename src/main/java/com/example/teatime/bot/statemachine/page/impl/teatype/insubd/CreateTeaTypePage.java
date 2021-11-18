package com.example.teatime.bot.statemachine.page.impl.teatype.insubd;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Objects;

import static java.util.Optional.ofNullable;

@Component
public class CreateTeaTypePage implements Page {
  private static final String[][] keyboard = new String[][]{
      {KeyTransitions.SAVE.getTitle()},
      {KeyTransitions.SET_TITLE.getTitle()},
      {KeyTransitions.SET_DESCR.getTitle()},
      {KeyTransitions.BACK.getTitle()},
      {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  @Override
  public SendMessage getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = MessageTools.makeSendMessage(receivedMessage);
    MessageTools.setKeyboard(keyboard, sendMessage);

    TeaType teaType = stateMachine.getDataManager().getObject(DataKeys.TEA_TYPE, TeaType.class);

    String text = "Создание нового вида чая." + "\n"
        + "Для создания необходимо заполнить все поля, и нажать на клавишу '" + KeyTransitions.SAVE.getTitle() + "'.\n"
        + "Для заполнения поля, найдите соответствующую клавишу на клавиатуре и нажмите ее. " + "\n" + "\n"
        + "Новый вид чая: " + "\n";

    if (Objects.nonNull(teaType)) {
      text = text +
          "Название - " + ofNullable(teaType.getTitle()).orElse("") + "\n" +
          "Описание - " + ofNullable(teaType.getDescription()).orElse("") + "\n";
    }

    sendMessage.setText(text);
    return sendMessage;
  }
}
