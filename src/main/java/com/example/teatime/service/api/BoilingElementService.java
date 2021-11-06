package com.example.teatime.service.api;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.BoilingElement;

import java.util.Optional;

public interface BoilingElementService {
  void save(BoilingElement boilingElement);

  void delete(BoilingElement boilingElement);

  boolean exist(BoilingElement boilingElement);

  Iterable<BoilingElement> listElementsByBoilingSortedByNumber(Boiling boiling);

  Optional<BoilingElement> findElementMaxNumberByBoiling(Boiling boiling);

  BoilingElement getElementById(Long id);

  ValidateResult validateWithMessage(BoilingElement boilingElement);
}
