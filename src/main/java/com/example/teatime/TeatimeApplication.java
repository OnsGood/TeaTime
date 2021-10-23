package com.example.teatime;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeatimeApplication {
  private static final Logger logger = LogManager.getLogger(TeatimeApplication.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    logger.trace("starting app");
    SpringApplication.run(TeatimeApplication.class, args);
  }

}
