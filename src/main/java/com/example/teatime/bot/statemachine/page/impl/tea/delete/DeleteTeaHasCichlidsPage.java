package com.example.teatime.bot.statemachine.page.impl.tea.delete;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Header;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class DeleteTeaHasCichlidsPage implements Page {

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    return new PageMessageBuilder(receivedMessage)
      .part(new Header("Данный чай содержит несколько заварок. Удалите их перед удалением чая!"))
      .buildMessageList();
  }
}
