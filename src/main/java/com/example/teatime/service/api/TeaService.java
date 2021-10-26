package com.example.teatime.service.api;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.entity.TeaType;

public interface TeaService {
  void save(Tea tea);

  Iterable<Tea> listTeaByName(String name);

  Iterable<Tea> listTeaByTeaType(TeaType teaType);

  Tea getTeaById(Long id);

  ValidateResult validateTeaWithMessage(Tea tea);
}
