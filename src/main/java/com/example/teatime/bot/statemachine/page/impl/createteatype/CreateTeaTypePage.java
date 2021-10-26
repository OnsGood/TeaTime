package com.example.teatime.bot.statemachine.page.impl.createteatype;

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
public class CreateTeaTypePage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.CREATE_TEA_TYPE.getTitle()},
    {KeyTransitions.SET_TITLE.getTitle()},
    {KeyTransitions.SET_DESCR.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = MessageTools.getSendMessage(receivedMessage);
    MessageTools.setKeyboard(keyboard, sendMessage);

    TeaType teaType = stateMachine.getDataManager().getObject(DataKeys.CREATED_TEA_TYPE, TeaType.class);

    String text = "Создание нового вида чая." + "\n"
        + "Для создания необходимо заполнить все поля, и повторно нажать на клавишу '" + KeyTransitions.CREATE_TEA_TYPE.getTitle() + "'.\n"
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
