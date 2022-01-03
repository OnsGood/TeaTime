package com.example.teatime.bot.statemachine.page.impl.teatype.see;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Header;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.SeeFacade;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.Text;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class SeeTeaTypePage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    long teaTypeId = LinkTransitions.getIdFromLink(receivedMessage.text());
    TeaType teatype = teaTypeService.getTeaTypeById(teaTypeId);

    return new PageMessageBuilder(receivedMessage)
      .keyboard(keyboard)
      .part(
        new SeeFacade()
          .setHeader(new Header("Вид чая '", teatype.getTitle(), "' "))
          .setDescription(new Text(teatype.getDescription()))
          .setGoMessage("Перейти к чаям")
          .setId(teaTypeId)
          .setShowEditAndDelete(stateMachine.isUserModerator())
      )
      .buildMessageList();
  }
}
