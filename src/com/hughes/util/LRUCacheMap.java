package com.hughes.util;

import java.util.LinkedHashMap;

public class LRUCacheMap<K,V> extends LinkedHashMap<K, V> {
  
  private int maxSize;
  
  public LRUCacheMap(final int maxSize) {
    super(maxSize, 0.75f, true);
    this.maxSize = maxSize;
  }

  @Override
  protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
    return this.size() > maxSize;
  }

}
