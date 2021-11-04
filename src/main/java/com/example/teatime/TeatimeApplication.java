package com.example.teatime;

import com.example.teatime.configuration.AppConfig;
import org.aeonbits.owner.ConfigFactory;
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
    AppConfig cs = ConfigFactory.create(AppConfig.class);
    dataSource.setDriverClassName(cs.bdDriver());
    dataSource.setUsername(cs.bdUsername());
    dataSource.setPassword(cs.bdPass());
    dataSource.setUrl(cs.bdUrl());
    return dataSource;
  }

}
