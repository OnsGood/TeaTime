package com.example.teatime.bd.repository.api;

import com.example.teatime.bd.entity.Moderator;
import org.springframework.data.repository.CrudRepository;

public interface ModeratorRepository extends CrudRepository<Moderator, Long> {

  boolean existsByTgId(Long tgId);
}
