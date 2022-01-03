package com.example.teatime.bot.statemachine.page.impl.tea.list;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bot.statemachine.StateMachine;
import com.example.teatime.bot.statemachine.transition.KeyTransitions;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeaListFromNamePage extends AbstractTeaListPage {
  private TeaService teaService;

  @Autowired
  public final void setTeaService(TeaService teaService) {
    this.teaService = teaService;
  }

  @Override
  protected Iterable<Tea> listTeas(String text) {
    return teaService.listTeaByName(text);
  }

  @Override
  protected String[][] getKeyboard(StateMachine stateMachine) {
    if (stateMachine.isUserModerator()) {
      return new String[][]{
        {KeyTransitions.CREATE_TEA.getTitle()},
        {KeyTransitions.TEA_NAME_SEARCH.getTitle()},
        {KeyTransitions.BACK.getTitle()},
        {KeyTransitions.MAIN_PAGE.getTitle()},
      };
    } else {
      return new String[][]{
        {KeyTransitions.TEA_NAME_SEARCH.getTitle()},
        {KeyTransitions.BACK.getTitle()},
        {KeyTransitions.MAIN_PAGE.getTitle()},
      };
    }
  }

  @Override
  protected String formStringFromTea(Tea tea) {
    return tea.getTitle() + "\n" +
      "Вид - " + tea.getTeaType().getTitle();
  }

  @Override
  protected String emptyListMessage() {
    return "Ничего не найдено";
  }
}
