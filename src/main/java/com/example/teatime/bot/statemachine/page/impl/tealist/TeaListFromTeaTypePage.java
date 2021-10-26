package com.example.teatime.bot.statemachine.page.impl.tealist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.MessageTools;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.bot.statemachine.transition.LinkTransitions;
import com.example.teatime.service.api.TeaService;
import com.example.teatime.service.api.TeaTypeService;

@Component
public class TeaListFromTeaTypePage extends AbstractTeaListPage {
  private TeaService teaService;
  private TeaTypeService teaTypeService;

  @Autowired
  public void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Autowired
  public void setTeaTypeService(TeaTypeService teaTypeService) {
    this.teaTypeService = teaTypeService;
  }

  @Override
  protected Iterable<Tea> listTeas(String text) {
    Long teaTypeId = MessageTools.getIdFromMessage(text);
    return teaService.listTeaByTeaType(teaTypeService.getTeaTypeById(teaTypeId));
  }

  @Override
  protected String[][] getKeyboard() {
    return new String[][]{
      {KeyTransitions.CREATE_TEA.getTitle()},
      {KeyTransitions.MAIN_PAGE.getTitle()},
    };
  }

  @Override
  protected String formStringFromTea(Tea tea) {
    return tea.getTitle() + "\n" +
      "Перейти - " + LinkTransitions.TEA.getPrefix() + tea.getId() + "\n";
  }
}
