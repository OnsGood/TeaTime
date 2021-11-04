package com.example.teatime.configuration;

import org.aeonbits.owner.Config;

@Config.Sources({"file:~/teatime/AppConfig.properties" })
public interface AppConfig extends Config {
  @Key("bot.name")
  String botName();

  @Key("bot.token")
  String botToken();

  @Key("bd.driver")
  String bdDriver();

  @Key("bd.username")
  String bdUsername();

  @Key("bd.password")
  String bdPass();

  @Key("bd.url")
  String bdUrl();
}
