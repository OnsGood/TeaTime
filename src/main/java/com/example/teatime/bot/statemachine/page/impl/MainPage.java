package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Br;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Header;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Text;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class MainPage implements Page {

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    PageMessageBuilder pageMessageBuilder = new PageMessageBuilder(receivedMessage)
      .keyboard(new String[][]{
        {KeyTransitions.TEA_TYPE_LIST.getTitle()},
        {KeyTransitions.TEA_NAME_SEARCH.getTitle()}}
      )
      .part(
        new Header("Вы находитесь в главном меню бота. Тыкайте на клавиши, и получайте что вам надо.")
      );

    if (stateMachine.isUserModerator()) {
      pageMessageBuilder
        .part(new Br())
        .part(new Text("Кстати, вы модератор."));
    }

    return pageMessageBuilder.buildMessageList();
  }
}
