package com.example.teatime.service.impl;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.repository.api.BoilingRepository;
import com.example.teatime.service.api.BoilingService;
import com.example.teatime.service.api.ValidateResult;

import static java.util.Objects.*;

@Service
public class BoilingServiceImpl implements BoilingService {
  private static final Logger logger = LogManager.getLogger(BoilingServiceImpl.class);
  private BoilingRepository boilingRepository;

  @Autowired
  public void setBoilingRepository(BoilingRepository boilingRepository) {
    this.boilingRepository = boilingRepository;
  }

  @Override
  public void save(Boiling boiling) {
    logger.info("save boiling - " + boiling.getTitle());
    boilingRepository.save(boiling);
  }

  @Override
  public void delete(Boiling boiling) {
    logger.info("delete boiling - " + boiling.getId());
    boilingRepository.delete(boiling);
  }

  @Override
  public boolean exist(Boiling boiling) {
    logger.info("check boiling exist");
    return Optional.ofNullable(boiling)
      .map(Boiling::getId)
      .map(id -> boilingRepository.existsById(id))
      .orElse(false);
  }

  @Override
  public Iterable<Boiling> listBoilingByTea(Tea tea) {
    logger.info("list boilings by tea - " + tea.getId());
    return boilingRepository.findByTea(tea);
  }

  @Override
  public Boiling getBoilingById(Long id) {
    logger.info("get boiling by id - " + id);
    return boilingRepository.findById(id)
      .orElseThrow(() -> new IllegalStateException("Boiling with id - '" + id + "' not found"));
  }

  @Override
  public ValidateResult validateWithMessage(Boiling boiling) {
    logger.info("validate boiling - " + boiling);
    String message = "";

    if (isNull(boiling.getTitle())) {
      message += "Не задано название. ";
    }
    if (isNull(boiling.getTea())) {
      message += "Не задан чай. ";
    }
    if (isNull(boiling.getDescription())) {
      message += "Не задано описание. ";
    }
    if (isNull(boiling.getActive())) {
      message += "Объект не активирован. ";
    }

    if (!message.equals("")) {
      return ValidateResult.getBad(message);
    }

    return ValidateResult.getGood();
  }
}
