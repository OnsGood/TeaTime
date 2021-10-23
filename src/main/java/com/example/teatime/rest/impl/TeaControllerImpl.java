package com.example.teatime.rest.impl;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.service.api.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeaControllerImpl {
    private TeaService teaService;

    @Autowired
    public void setTeaService(TeaService teaService) {
        this.teaService = teaService;
    }

    @GetMapping("/tea")
    public List<Tea> listAll() {
        return teaService.listTea();
    }
}
