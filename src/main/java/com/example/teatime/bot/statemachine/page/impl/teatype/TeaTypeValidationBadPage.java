package com.example.teatime.bot.statemachine.page.impl.teatype;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class TeaTypeValidationBadPage implements Page {

  @Override
  public SendMessage getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    sendMessage.setText("Вид чая не прошел валидацию! Похоже некоторые поля не заполнены");
    return sendMessage;
  }
}
