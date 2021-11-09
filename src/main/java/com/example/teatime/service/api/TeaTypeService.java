package com.example.teatime.service.api;

import com.example.teatime.bd.entity.TeaType;

public interface TeaTypeService {
  void save(TeaType teaType);

  Iterable<TeaType> listTeaType();

  TeaType getTeaTypeById(Long id);

  ValidateResult validateWithMessage(TeaType teaType);

  boolean exist(TeaType teaType);

  void delete(TeaType teaType);

  boolean isAllowedToDelete(TeaType teaType);
}
