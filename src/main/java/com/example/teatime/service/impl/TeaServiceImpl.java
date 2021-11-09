package com.example.teatime.service.impl;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bd.repository.api.TeaRepository;
import com.example.teatime.service.api.BoilingService;
import com.example.teatime.service.api.TeaService;
import com.example.teatime.service.api.ValidateResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class TeaServiceImpl implements TeaService {
  private static final Logger logger = LogManager.getLogger(TeaServiceImpl.class);
  private TeaRepository teaRepository;

  private BoilingService boilingService;

  @Autowired
  public void setBoilingService(BoilingService boilingService) {
    this.boilingService = boilingService;
  }

  @Autowired
  public void setTeaRepository(TeaRepository teaRepository) {
    this.teaRepository = teaRepository;
  }

  @Override
  public void save(Tea tea) {
    teaRepository.save(tea);
  }

  @Override
  public void delete(Tea tea) {
    teaRepository.delete(tea);
  }

  @Override
  public boolean exist(Tea tea) {
    return Optional.ofNullable(tea)
        .map(Tea::getId)
        .map(id -> teaRepository.existsById(id))
        .orElse(false);
  }

  @Override
  public Iterable<Tea> listTeaByName(String name) {
    logger.info("list teas by name - " + name);
    return teaRepository.findByTitleLikeIgnoreCase(name);
  }

  @Override
  public Iterable<Tea> listTeaByTeaType(TeaType teaType) {
    logger.info("list teas by teaType - " + teaType.getId());
    return teaRepository.findByTeaType(teaType);
  }

  @Override
  public long countTeaByTeaType(TeaType teaType) {
    logger.info("count teas by teaType - " + teaType.getId());
    return teaRepository.countByTeaType(teaType);
  }

  @Override
  public Tea getTeaById(Long id) {
    logger.info("list teas by id - " + id);
    return teaRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("Tea with id - '" + id + "' not found"));
  }

  @Override
  public boolean isAllowedToDelete(Tea tea) {
    logger.info("check tea allowed to delete " + tea.getId());
    return boilingService.countByTea(tea) == 0;
  }

  @Override
  public ValidateResult validateTeaWithMessage(Tea tea) {
    String message = "";

    if (isNull(tea.getTeaType())) {
      message += "Не задан тип чая. ";
    }
    if (isNull(tea.getTitle())) {
      message += "Не задано название чая. ";
    }
    if (isNull(tea.getDescription())) {
      message += "Не задано описание чая. ";
    }
    if (isNull(tea.getActive())) {
      message += "Объект не активирован. ";
    }

    if (!message.equals("")) {
      return ValidateResult.getBad(message);
    }

    return ValidateResult.getGood();
  }
}
