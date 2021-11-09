package com.example.teatime.bd.repository.api;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.BoilingElement;
import org.springframework.data.repository.CrudRepository;

public interface BoilingElementRepository extends CrudRepository<BoilingElement, Long> {

  Iterable<BoilingElement> findByBoilingOrderByNumber(Boiling boiling);

  BoilingElement findFirstByBoilingOrderByNumberDesc(Boiling boiling);

  long countByBoiling(Boiling boiling);
}
