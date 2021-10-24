package com.example.teatime.service.impl;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bd.repository.api.TeaRepository;
import com.example.teatime.bot.statemachine.state.api.ValidateResult;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TeaServiceImpl implements TeaService {
  private TeaRepository teaRepository;

  @Autowired
  public void setTeaRepository(TeaRepository teaRepository) {
    this.teaRepository = teaRepository;
  }

  @Override
  public void save(Tea tea) {
    teaRepository.save(tea);
  }

  @Override
  public Iterable<Tea> listTeaByName(String name) {
    return teaRepository.findByTitleLikeIgnoreCase(name);
  }

  @Override
  public Iterable<Tea> listTeaByTeaType(TeaType teaType) {
    return teaRepository.findByTeaType(teaType);
  }

  @Override
  public ValidateResult validateTeaWithMessage(Tea tea) {
    String message = "";

    if (Objects.isNull(tea.getTeaType())) {
      message += "Не задан тип чая. ";
    }
    if (Objects.isNull(tea.getTitle())) {
      message += "Не задано название чая. ";
    }
    if (Objects.isNull(tea.getDescription())) {
      message += "Не задано описание чая. ";
    }
    if (Objects.isNull(tea.getActive())) {
      message += "Объект не активирован. ";
    }

    if (!message.equals("")) {
      return ValidateResult.getBad(message);
    }

    return ValidateResult.getGood();
  }
}
