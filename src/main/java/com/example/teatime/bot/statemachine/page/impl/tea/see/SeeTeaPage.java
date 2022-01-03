package com.example.teatime.bot.statemachine.page.impl.tea.see;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Header;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.SeeFacade;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Text;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class SeeTeaPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private TeaService teaService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    long teaId = LinkTransitions.getIdFromLink(receivedMessage.text());
    Tea tea = teaService.getTeaById(teaId);

    return new PageMessageBuilder(receivedMessage)
      .keyboard(keyboard)
      .part(new SeeFacade()
        .setHeader(
          new Header("Чай '", tea.getTitle(), "' вида '", tea.getTeaType().getTitle(), "'")
        )
        .setDescription(new Text(tea.getDescription()))
        .setGoMessage("Перейти к способам заварки")
        .setId(teaId)
        .setShowEditAndDelete(stateMachine.isUserModerator())
      )
      .buildMessageList();
  }
}
