package com.example.teatime.bot.statemachine.page.impl.teatype.delete;

import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;

import static com.example.teatime.bot.statemachine.MessageTools.*;

@Component
public class DeleteTeaTypeHasCichlidsPage implements Page {

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    sendMessage.setText("Данный вид чая содержит несколько чаев. Удалите их перед удалением вида!");
    return List.of(sendMessage);
  }
}
