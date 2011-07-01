package com.hughes.util;

import java.util.List;

public final class ListUtil {
  
  public static final <T> T getLast(final List<T> list) {
    return list.get(list.size() - 1);
  }
  
}
