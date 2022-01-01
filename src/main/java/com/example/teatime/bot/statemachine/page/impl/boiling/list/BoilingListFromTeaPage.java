package com.example.teatime.bot.statemachine.page.impl.boiling.list;

import java.util.List;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.BoilingService;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.teatime.bot.statemachine.MessageTools.makeSendMessage;
import static com.example.teatime.bot.statemachine.MessageTools.setKeyboard;

@Component
public class BoilingListFromTeaPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private static final String[][] moderKeyboard = new String[][]{
    {KeyTransitions.CREATE_BOILING.getTitle()},
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private TeaService teaService;
  private BoilingService boilingService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    SendMessage sendMessage = makeSendMessage(receivedMessage);
    setKeyboard(keyboard, moderKeyboard, sendMessage, stateMachine.isUserModerator());
    StringBuilder builder = new StringBuilder();

    Long teaId = LinkTransitions.getIdFromLink(receivedMessage.text());

    Iterable<Boiling> boilings = boilingService.listBoilingByTea(teaService.getTeaById(teaId));

    if (boilings.iterator().hasNext()) {
      builder.append("Найдены способы заварки : ")
        .append("\n")
        .append("\n");

      boilings.forEach(boiling -> builder
        .append(formStringFromBoiling(boiling))
        .append("\n")
      );
    } else {
      builder.append("Способы заварки для этого чая не найдены. Создайте их.");
    }

    sendMessage.setText(builder.toString());
    return List.of(sendMessage);
  }

  private String formStringFromBoiling(Boiling boiling) {
    return boiling.getTitle() + "\n" +
      "Описание - " + boiling.getDescription() + "\n" +
      "Перейти - " + LinkTransitions.GO.getPrefix() + boiling.getId() + "\n";
  }
}
