package com.example.teatime.service.impl;

import com.example.teatime.bd.repository.api.ModeratorRepository;
import com.example.teatime.service.api.ModeratorService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeratorServiceImpl implements ModeratorService {
  private static final Logger logger = LogManager.getLogger(ModeratorServiceImpl.class);
  private ModeratorRepository moderatorRepository;

  @Autowired
  public void setModeratorRepository(ModeratorRepository moderatorRepository) {
    this.moderatorRepository = moderatorRepository;
  }

  @Override
  public boolean isUserModerator(Long userId) {
    logger.info("check user is moderator - " + userId);
    return moderatorRepository.existsByTgId(userId);
  }
}
