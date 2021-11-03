package com.example.teatime.service.api;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.Tea;

public interface BoilingService {
  void save(Boiling boiling);

  void delete(Boiling boiling);

  boolean exist(Boiling boiling);

  Iterable<Boiling> listBoilingByTea(Tea tea);

  Boiling getBoilingById(Long id);

  ValidateResult validateWithMessage(Boiling boiling);
}
