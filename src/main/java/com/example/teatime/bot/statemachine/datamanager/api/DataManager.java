package com.example.teatime.bot.statemachine.datamanager.api;

import java.util.Set;

/**
 * Хранитель дополнительных данных машины состояний
 */
public interface DataManager {

  /**
   * Установить объект
   */
  void setObject(DataKeys key, Object object);

  /**
   * Получить объект
   */
  <T> T getObject(DataKeys key, Class<T> objectClass);

  void updateData(Set<DataKeys> allowedDataKeys);
}
