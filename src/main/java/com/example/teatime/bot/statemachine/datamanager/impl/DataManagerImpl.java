package com.example.teatime.bot.statemachine.datamanager.impl;

import com.example.teatime.bot.statemachine.datamanager.api.DataKeys;
import com.example.teatime.bot.statemachine.datamanager.api.DataManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataManagerImpl implements DataManager {
  private static final Logger logger = LogManager.getLogger(DataManagerImpl.class);
  private final Map<DataKeys, Object> objectMap;

  public DataManagerImpl() {
    objectMap = new HashMap<>();
  }

  @Override
  public void setObject(DataKeys key, Object object) {
    Object prevObj = objectMap.get(key);
    if (Objects.isNull(prevObj)) {
      objectMap.put(key, object);
    } else {
      objectMap.replace(key, object);
    }
  }

  @Override
  public <T> T getObject(DataKeys key, Class<T> objectClass) {
    Object object = objectMap.get(key);
    if (Objects.isNull(object)) return null;

    if (objectClass.isInstance(object)) {
      return objectClass.cast(object);
    }
    throw new NullPointerException("Object not found");
  }
}
