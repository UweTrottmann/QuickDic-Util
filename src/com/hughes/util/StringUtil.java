package com.hughes.util;

public final class StringUtil {
  
  public static String reverse(final String s) {
    final StringBuilder builder = new StringBuilder(s);
    for (int i = 0; i < s.length(); ++i) {
      builder.setCharAt(i, s.charAt(s.length() - 1 - i));
    }
    return builder.toString();
  }

}
