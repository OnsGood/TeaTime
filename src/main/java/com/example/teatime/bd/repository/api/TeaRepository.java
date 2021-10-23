package com.example.teatime.bd.repository.api;

import com.example.teatime.bd.entity.Tea;

import java.util.List;

public interface TeaRepository {
    List<Tea> listTea();

    Tea getTeaById(Long id);

    Tea getTeaByTitle(String title);
}
