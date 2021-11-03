package com.example.teatime.bd.repository.api;

import org.springframework.data.repository.CrudRepository;

import com.example.teatime.bd.entity.Boiling;
import com.example.teatime.bd.entity.Tea;

public interface BoilingRepository extends CrudRepository<Boiling, Long> {

  Iterable<Boiling> findByTea(Tea tea);
}
