package com.example.teatime.service.api;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.entity.TeaType;
import com.example.teatime.bot.statemachine.state.api.ValidateResult;

public interface TeaService {
    void save(Tea tea);
    Iterable<Tea> listTeaByName(String name);
    Iterable<Tea> listTeaByTeaType(TeaType teaType);

    ValidateResult validateTeaWithMessage(Tea tea);
}
