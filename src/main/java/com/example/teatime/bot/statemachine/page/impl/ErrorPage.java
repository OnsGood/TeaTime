package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.api.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.teatime.bot.statemachine.MessageTools.makeSendMessage;

@Component
public class ErrorPage implements Page {
  @Override
  public SendMessage getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    String exceptionText = stateMachine.getDataManager().getObject(DataKeys.ERROR, Exception.class)
      .getLocalizedMessage();


    sendMessage.setText(
      "При обработке сообщения произошла ошибка." + "\n" +
        exceptionText + "\n" +
        "Возврат в главное меню."
    );
    return sendMessage;
  }
}
