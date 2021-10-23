package com.example.teatime.bd.repository.impl;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.repository.api.DatabaseSearchException;
import com.example.teatime.bd.repository.api.TeaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.text.MessageFormat.format;

@Component
public class TeaRepositoryImpl implements TeaRepository {
    private final List<Tea> teaList;

    public TeaRepositoryImpl() {
        teaList = Collections.unmodifiableList(List.of(
                new Tea(1L, "Черный"),
                new Tea(2L, "Зеленый"),
                new Tea(3L, "Улун"),
                new Tea(4L, "Габа"),
                new Tea(5L, "Пуэр")
        ));
    }

    @Override
    public List<Tea> listTea() {
        return new ArrayList<>(teaList);
    }

    @Override
    public Tea getTeaById(Long id) {
        return teaList.stream()
                .filter(t -> t.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new DatabaseSearchException(format("Не найден tea с id = {0}. ", id)));
    }

    @Override
    public Tea getTeaByTitle(String title) {
        return teaList.stream()
                .filter(t -> t.getTitle().equals(title))
                .findAny()
                .orElseThrow(() -> new DatabaseSearchException(format("Не найден tea с title = {0}. ", title)));
    }
}
