package com.example.teatime.service.api;

import com.example.teatime.bd.entity.TeaType;

public interface TeaTypeService {
  void save(TeaType teaType);

  Iterable<TeaType> listTeaType();

  TeaType getTeaTypeById(Long id);
}
