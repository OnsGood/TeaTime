package com.example.teatime.bot.statemachine.page.impl;

import java.util.List;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.teatime.bot.statemachine.MessageTools.makeSendMessage;

@Component
public class DeleteWithConfirmPage implements Page {

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    MessageTools.removeSpecialKeyboard(sendMessage);
    sendMessage.setText("Если вы действительно хотите удалить, то введите \"Да\":");
    return List.of(sendMessage);
  }
}
