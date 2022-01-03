package com.example.teatime.bot.statemachine.page.impl.tea.insubd;

import java.util.List;

import com.example.teatime.bot.statemachine.page.pagebuilder.PageMessageBuilder;
import com.example.teatime.bot.statemachine.page.pagebuilder.impl.GoListFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.life.MessageDto;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.page.api.Page;
import com.example.teatime.service.api.TeaTypeService;

@Component
public class TeaChooseTeaTypeListPage implements Page {
  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }

  @Override
  public List<SendMessage> getPageMessage(MessageDto receivedMessage, StateMachine stateMachine) {
    Iterable<TeaType> teas = teaTypeService.listTeaType();
    return new PageMessageBuilder(receivedMessage)
      .removeKeyboard()
      .part(new GoListFacade<TeaType>()
        .setHead("Выберите вид чая из нижеперечисленных:")
        .setList(teas)
        .setToIdFunction(TeaType::getId)
        .setToTextFunction(TeaType::getTitle)
        .setNoFoundMessage("Виды чаев не найдены. Вначале нужно создать хотя бы один.")
      )
      .buildMessageList();
  }
}
