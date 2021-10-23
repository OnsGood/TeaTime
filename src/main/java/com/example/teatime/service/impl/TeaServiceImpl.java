package com.example.teatime.service.impl;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.repository.api.TeaRepository;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaServiceImpl implements TeaService {
    private TeaRepository teaRepository;

    @Autowired
    public void setTeaRepository(TeaRepository teaRepository) {
        this.teaRepository = teaRepository;
    }


    @Override
    public List<Tea> listTea() {
        return teaRepository.listTea();
    }
}
