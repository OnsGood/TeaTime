package com.example.teatime.bot.statemachine.page.impl;

import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.teatime.bot.statemachine.MessageTools.getSendMessage;
import static com.example.teatime.bot.statemachine.MessageTools.setKeyboard;

@Component
public class TeaTypeListPage implements Page {
  private static final String[][] keyboard = new String[][]{
      {KeyTransitions.MAIN_PAGE.getTitle()},
      {KeyTransitions.CREATE_TEA_TYPE.getTitle()}
  };

  @Override
  public SendMessage getPageMessage(Message receivedMessage) {
    SendMessage sendMessage = getSendMessage(receivedMessage);
    setKeyboard(keyboard, sendMessage);

    String head = "Отображаем список типов чаев:";

    String builder = head +
        "\n" +
        "\n" +
        getElement(1L) +
        getElement(2L) +
        getElement(3L) +
        getElement(4L) +
        "\n";

    sendMessage.setText(builder);

    return sendMessage;
  }

  private String getElement(Long teaTypeId) {
    String descr = "Вид чая " + teaTypeId;
    String downloadLink = LinkTransitions.GET_TEA_TYPE.getPrefix() + teaTypeId;
    return descr + "\n" + downloadLink + "\n" + "\n";
  }
}
