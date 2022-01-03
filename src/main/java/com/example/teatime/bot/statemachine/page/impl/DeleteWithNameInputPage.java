package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Header;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class DeleteWithNameInputPage implements Page {

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    return new PageMessageBuilder(receivedMessage)
      .part(new Header("Если вы действительно хотите этого, то введите имя удаляемого объекта:"))
      .removeKeyboard()
      .buildMessageList();
  }
}
