package com.example.teatime.bd.repository.test;
import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.repository.impl.TeaRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TeaRepositoryTest {
    private TeaRepositoryImpl teaRepository;

    @Before
    public void init() {
        teaRepository = new TeaRepositoryImpl();
    }


    @Test
    public void goodReturnTeas() {
        List<Tea> list = teaRepository.listTea();
        System.out.println(list);
        Assert.assertNotNull(list.get(0));
        Assert.assertNotNull(teaRepository.getTeaById(1L));
    }
}
