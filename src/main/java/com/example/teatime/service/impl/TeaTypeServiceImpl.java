package com.example.teatime.service.impl;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bd.repository.api.TeaTypeRepository;
import com.example.teatime.service.api.TeaTypeService;
import com.example.teatime.service.api.ValidateResult;

import static java.util.Objects.*;

@Service
public class TeaTypeServiceImpl implements TeaTypeService {
  private static final Logger logger = LogManager.getLogger(TeaTypeServiceImpl.class);

  private TeaTypeRepository teaTypeRepository;

  @Autowired
  public void setTeaTypeRepository(TeaTypeRepository teaTypeRepository) {
    this.teaTypeRepository = teaTypeRepository;
  }

  @Override
  public void save(TeaType teaType) {
    logger.info("save teaType - " + teaType.getTitle());
    teaTypeRepository.save(teaType);
  }

  @Override
  public Iterable<TeaType> listTeaType() {
    logger.info("list teaTypes ");
    return teaTypeRepository.findAll();
  }

  @Override
  public TeaType getTeaTypeById(Long id) {
    logger.info("get teaType by id - " + id);
    return teaTypeRepository.findById(id)
      .orElseThrow();
  }

  @Override
  public ValidateResult validateWithMessage(TeaType teaType) {
    logger.info("validate teaType - " + teaType.getId());
    String message = "";

    if (isNull(teaType.getTitle())) {
      message += "Не задано название. ";
    }
    if (isNull(teaType.getDescription())) {
      message += "Не задано описание. ";
    }
    if (isNull(teaType.getActive())) {
      message += "Объект не активирован. ";
    }

    if (!message.equals("")) {
      return ValidateResult.getBad(message);
    }

    return ValidateResult.getGood();
  }

  @Override
  public boolean exist(TeaType teaType) {
    logger.info("check teaType exist");
    return Optional.ofNullable(teaType)
      .map(TeaType::getId)
      .map(id -> teaTypeRepository.existsById(id))
      .orElse(false);
  }
}
