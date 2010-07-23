package com.hughes.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class Args {

  public static Map<String, String> keyValueArgs(final String[] args) {
    final Map<String,String> dest = new LinkedHashMap<String, String>();
    for (int i = 0; i < args.length; ++i) {
      int equalsIndex;
      if (args[i].startsWith("--") && (equalsIndex = args[i].indexOf("=")) >= 0) {
        final String key = args[i].substring(2, equalsIndex);
        final String value = args[i].substring(equalsIndex + 1, args[i].length());
        dest.put(key, value);
      }
    }
    return dest;
  }
  
}
