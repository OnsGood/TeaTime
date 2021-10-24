package com.example.teatime.bot.statemachine.page.impl.createtea;

import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.teatime.bot.statemachine.MessageTools.getSendMessage;

@Component
public class CreateTeaValidationBadPage implements Page {

  @Override
  public SendMessage getPageMessage(Message receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = getSendMessage(receivedMessage);
    sendMessage.setText("Чай не прошел валидацию! Похоже некоторые поля еще не заполнены");
    return sendMessage;
  }
}
