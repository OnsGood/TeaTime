package com.example.teatime.bot.statemachine.page.impl.boilingelement.insubd;

import com.example.teatime.bd.entity.BoilingElement;
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
public class CreateBoilingElementPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.SAVE.getTitle()},
    {KeyTransitions.SET_TIME.getTitle()},
    {KeyTransitions.SET_TEMP.getTitle()},
    {KeyTransitions.SET_MASS.getTitle()},
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  @Override
  public SendMessage getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = MessageTools.makeSendMessage(receivedMessage);
    MessageTools.setKeyboard(keyboard, sendMessage);

    BoilingElement boilingElement = stateMachine.getDataManager().getObject(DataKeys.BOILING_ELEMENT, BoilingElement.class);

    String text = """
      Добавление новой заварки.

      """;

    if (Objects.nonNull(boilingElement)) {
      text = text +
        "Номер заварки - " + ofNullable(boilingElement.getNumber()).orElse(0L) + "\n" +
        "Время (секунды) - " + ofNullable(boilingElement.getSeconds()).orElse(0L) + "\n" +
        "Температура - " + ofNullable(boilingElement.getTemperature()).orElse(0L) + "\n" +
        "Масса (граммы) - " + ofNullable(boilingElement.getMass()).orElse(0L) + "\n";
    }

    sendMessage.setText(text);
    return sendMessage;
  }
}
