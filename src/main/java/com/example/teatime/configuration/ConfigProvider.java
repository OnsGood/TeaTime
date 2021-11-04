package com.example.teatime.configuration;

import org.aeonbits.owner.ConfigFactory;
import org.springframework.stereotype.Component;

@Component
public class ConfigProvider {
  private final AppConfig appConfig;

  public ConfigProvider() {
    appConfig = ConfigFactory.create(AppConfig.class);
  }

  public AppConfig get() {
    return appConfig;
  }

}
