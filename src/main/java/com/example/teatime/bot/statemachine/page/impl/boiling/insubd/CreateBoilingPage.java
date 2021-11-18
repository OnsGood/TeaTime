package com.example.teatime.bot.statemachine.page.impl.boiling.insubd;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;

import static java.util.Optional.*;

@Component
public class CreateBoilingPage implements Page {
  private static final String[][] keyboard = new String[][]{
      {KeyTransitions.SAVE.getTitle()},
      {KeyTransitions.SET_TITLE.getTitle()},
      {KeyTransitions.SET_DESCR.getTitle()},
      {KeyTransitions.BACK.getTitle()},
      {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = MessageTools.makeSendMessage(receivedMessage);
    MessageTools.setKeyboard(keyboard, sendMessage);

    Boiling boiling = stateMachine.getDataManager().getObject(DataKeys.BOILING, Boiling.class);

    String text = "Создание нового способа заварки." + "\n"
        + "Для создания необходимо заполнить все поля, и нажать на клавишу '" + KeyTransitions.SAVE.getTitle() + "'.\n"
        + "Для заполнения поля, найдите соответствующую клавишу на клавиатуре и нажмите ее. " + "\n" + "\n"
        + "Новый способ заварки: " + "\n";


    if (Objects.nonNull(boiling)) {
      String teaTitle = ofNullable(boiling.getTea())
          .map(Tea::getTitle)
          .orElse("");

      text = text +
          "Название - " + ofNullable(boiling.getTitle()).orElse("") + "\n" +
          "Описание - " + ofNullable(boiling.getDescription()).orElse("") + "\n" +
          "Чай - " + teaTitle + "\n";
    }

    sendMessage.setText(text);
    return List.of(sendMessage);
  }
}
