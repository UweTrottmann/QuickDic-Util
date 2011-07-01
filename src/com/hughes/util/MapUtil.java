package com.hughes.util;

import java.util.Map;

public class MapUtil {
  
  public static <K,V> V safeGet(final Map<K,V> map, K key, V defaultValue) {
    if (!map.containsKey(key)) {
      return defaultValue;
    }
    return map.get(key);
  }

  public static <K,V> V safeGetOrPut(final Map<K,V> map, K key, V defaultValue) {
    if (!map.containsKey(key)) {
      map.put(key, defaultValue);
    }
    return map.get(key);
  }

  public static <K,V> V safeGet(final Map<K,V> map, K key, Class<V> valueClass) {
    if (!map.containsKey(key)) {
      try {
        map.put(key, valueClass.newInstance());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return map.get(key);
  }

}
