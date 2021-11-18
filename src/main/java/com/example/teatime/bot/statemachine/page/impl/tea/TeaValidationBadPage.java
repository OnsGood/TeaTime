package com.example.teatime.bot.statemachine.page.impl.tea;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.teatime.bot.statemachine.MessageTools.makeSendMessage;

@Component
public class TeaValidationBadPage implements Page {

  @Override
  public SendMessage getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    sendMessage.setText("Чай не прошел валидацию! Похоже некоторые поля не заполнены");
    return sendMessage;
  }
}
