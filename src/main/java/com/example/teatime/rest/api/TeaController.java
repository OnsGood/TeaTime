package com.example.teatime.rest.api;

import com.example.teatime.bd.entity.Tea;

import java.util.List;

public interface TeaController {
    List<Tea> listAll();
}
