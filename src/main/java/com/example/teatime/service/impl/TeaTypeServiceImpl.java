package com.example.teatime.service.impl;

import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bd.repository.api.TeaTypeRepository;
import com.example.teatime.service.api.TeaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeaTypeServiceImpl implements TeaTypeService {
  private TeaTypeRepository teaTypeRepository;

  @Autowired
  public void setTeaTypeRepository(TeaTypeRepository teaTypeRepository) {
    this.teaTypeRepository = teaTypeRepository;
  }

  @Override
  public void save(TeaType teaType) {
    teaTypeRepository.save(teaType);
  }

  @Override
  public Iterable<TeaType> listTeaType() {
    return teaTypeRepository.findAll();
  }

  @Override
  public TeaType getTeaTypeById(Long id) {
    return teaTypeRepository.findById(id)
        .orElseThrow();
  }
}
