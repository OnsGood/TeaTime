package com.example.teatime.bd.repository.api;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.entity.TeaType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeaRepository extends CrudRepository<Tea, Long> {

  List<Tea> findByTitleLikeIgnoreCase(String title);

  Iterable<Tea> findByTeaType(TeaType teaType);

}
