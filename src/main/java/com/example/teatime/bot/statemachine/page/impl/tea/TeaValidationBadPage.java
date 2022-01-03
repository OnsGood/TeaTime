package com.example.teatime.bot.statemachine.page.impl.tea;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Header;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class TeaValidationBadPage implements Page {

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    return new PageMessageBuilder(receivedMessage)
      .part(new Header("Чай не прошел валидацию! Похоже некоторые поля не заполнены."))
      .buildMessageList();
  }
}
