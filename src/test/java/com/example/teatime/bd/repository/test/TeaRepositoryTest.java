package com.example.teatime.bd.repository.test;

import com.example.teatime.bd.entity.Tea;
import com.example.teatime.bd.repository.api.TeaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@Deprecated
@RunWith(SpringRunner.class)
@DataJpaTest
public class TeaRepositoryTest {
  private TeaRepository teaRepository;

  @Autowired
  public void setTeaRepository(TeaRepository teaRepository) {
    this.teaRepository = teaRepository;
  }

  public void goodReturnTeas() {
    Iterable<Tea> list = teaRepository.findAll();
    System.out.println(list);
  }
}
