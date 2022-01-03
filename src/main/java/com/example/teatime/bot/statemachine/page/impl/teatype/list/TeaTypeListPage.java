package com.example.teatime.bot.statemachine.page.impl.teatype.list;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.GoListFacade;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.service.api.TeaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class TeaTypeListPage implements Page {
  private static final String[][] keyboard = new String[][]{
    {KeyTransitions.BACK.getTitle()},
    {KeyTransitions.MAIN_PAGE.getTitle()},
  };

  private static final String[][] moderKeyboard = new String[][]{
    {KeyTransitions.CREATE_TEA_TYPE.getTitle()},
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
    Iterable<TeaType> teaTypes = teaTypeService.listTeaType();
    return new PageMessageBuilder(receivedMessage)
      .keyboard(stateMachine.isUserModerator() ? moderKeyboard : keyboard)
      .part(new GoListFacade<TeaType>()
        .setHead("Виды чаев:")
        .setList(teaTypes)
        .setToIdFunction(TeaType::getId)
        .setToTextFunction(TeaType::getTitle)
        .setNoFoundMessage("Виды чаев не найдены")
      )
      .buildMessageList();
  }
}
