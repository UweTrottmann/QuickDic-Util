package com.hughes.util;

import java.util.AbstractList;
import java.util.List;

public class CachingList<T> extends AbstractList<T> {
  
  private final List<T> list;
  private final int size;
  
  private final LRUCacheMap<Integer, T> cache;

  public CachingList(final List<T> list, final int cacheSize) {
    this.list = list;
    size = list.size();
    cache = new LRUCacheMap<Integer, T>(cacheSize);
  }
  
  public static <T> CachingList<T> create(final List<T> list, final int cacheSize) {
    return new CachingList<T>(list, cacheSize);
  }

  public static <T> CachingList<T> createFullyCached(final List<T> list) {
    return new CachingList<T>(list, list.size());
  }

  @Override
  public T get(int i) {
    T t = cache.get(i);
    if (t == null) {
      t = list.get(i);
      cache.put(i, t);
    }
    return t;
  }

  @Override
  public int size() {
    return size;
  }

}
