package com.example.teatime.bot.statemachine.datamanager.api;

import java.util.Collections;
import java.util.Set;

/**
 * Показывает с какими типами данных может работать объект
 */
public interface DataSupportable {

  /**
   * @return set ключей данных, с которыми умеет работать объект
   */
  default Set<DataKeys> getSupportedData() {
    return Collections.emptySet();
  }
}
