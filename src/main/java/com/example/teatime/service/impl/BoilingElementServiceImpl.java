package com.example.teatime.service.impl;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.BoilingElement;
import com.example.teatime.bd.repository.api.BoilingElementRepository;
import com.example.teatime.service.api.BoilingElementService;
import com.example.teatime.service.api.ValidateResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class BoilingElementServiceImpl implements BoilingElementService {
  private static final Logger logger = LogManager.getLogger(BoilingElementServiceImpl.class);
  private BoilingElementRepository boilingElementRepository;

  @Autowired
  public void setBoilingElementRepository(BoilingElementRepository boilingElementRepository) {
    this.boilingElementRepository = boilingElementRepository;
  }

  @Override
  public void save(BoilingElement boilingElement) {
    logger.info("save boiling element");
    boilingElementRepository.save(boilingElement);
  }

  @Override
  public void delete(BoilingElement boilingElement) {
    logger.info("delete boiling element - " + boilingElement.getId());
    boilingElementRepository.delete(boilingElement);
  }

  @Override
  public boolean exist(BoilingElement boilingElement) {
    logger.info("check boiling element exist");
    return Optional.ofNullable(boilingElement)
      .map(BoilingElement::getId)
      .map(id -> boilingElementRepository.existsById(id))
      .orElse(false);
  }

  @Override
  public Iterable<BoilingElement> listElementsByBoilingSortedByNumber(Boiling boiling) {
    logger.info("list boiling elements by boiling - " + boiling.getId());
    return boilingElementRepository.findByBoilingOrderByNumber(boiling);
  }

  @Override
  public Optional<BoilingElement> findElementMaxNumberByBoiling(Boiling boiling) {
    logger.info("find boiling element with max num by boiling - " + boiling.getId());
    return Optional.ofNullable(
      boilingElementRepository.findFirstByBoilingOrderByNumberDesc(boiling)
    );
  }

  @Override
  public BoilingElement getElementById(Long id) {
    logger.info("get boiling element by id - " + id);
    return boilingElementRepository.findById(id)
      .orElseThrow(() -> new IllegalStateException("Boiling element with id - '" + id + "' not found"));
  }

  @Override
  public ValidateResult validateWithMessage(BoilingElement boilingElement) {
    logger.info("validate boiling element - " + boilingElement.getId());
    String message = "";

    if (isNull(boilingElement.getNumber())) {
      message += "Не задан номер. ";
    }
    if (isNull(boilingElement.getSeconds())) {
      message += "Не задано время заваривания. ";
    }
    if (isNull(boilingElement.getTemperature())) {
      message += "Не задана температура. ";
    }
    if (isNull(boilingElement.getActive())) {
      message += "Объект не активирован. ";
    }

    if (!message.equals("")) {
      return ValidateResult.getBad(message);
    }

    return ValidateResult.getGood();
  }

  @Override
  public long countByBoiling(Boiling boiling) {
    logger.info("count boiling elements by boiling - " + boiling.getId());
    return boilingElementRepository.countByBoiling(boiling);
  }

}
