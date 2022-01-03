package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Header;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Text;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class ErrorPage implements Page {
  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    String exceptionText = stateMachine.getDataManager().getObject(DataKeys.ERROR, Exception.class)
      .getLocalizedMessage();

    return new PageMessageBuilder(receivedMessage)
      .part(new Header("При обработке сообщения произошла ошибка."))
      .part(new Text(exceptionText))
      .part(new Text("Возврат в главное меню."))
      .removeKeyboard()
      .buildMessageList();
  }
}
