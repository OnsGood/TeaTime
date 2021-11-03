package com.example.teatime;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class TeatimeApplication {
  private static final Logger logger = LogManager.getLogger(TeatimeApplication.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    logger.trace("starting app");
    SpringApplication.run(TeatimeApplication.class, args);
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUsername("postgres");
    dataSource.setPassword("postgres");
    dataSource.setUrl("jdbc:postgresql://localhost:5433/postgres");
    return dataSource;
  }

}
