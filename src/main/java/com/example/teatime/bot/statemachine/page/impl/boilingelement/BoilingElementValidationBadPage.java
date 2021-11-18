package com.example.teatime.bot.statemachine.page.impl.boilingelement;

import java.util.List;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.teatime.bot.statemachine.MessageTools.makeSendMessage;

@Component
public class BoilingElementValidationBadPage implements Page {

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    sendMessage.setText("Заварка не прошла валидацию! Похоже некоторые поля не заполнены");
    return List.of(sendMessage);
  }
}
