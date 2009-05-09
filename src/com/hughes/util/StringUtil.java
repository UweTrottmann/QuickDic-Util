package com.hughes.util;

public final class StringUtil {
  
  public static String reverse(final String s) {
    final StringBuilder builder = new StringBuilder(s);
    for (int i = 0; i < s.length(); ++i) {
      builder.setCharAt(i, s.charAt(s.length() - 1 - i));
    }
    return builder.toString();
  }

  public static String flipCase(final String s) {
    final StringBuilder builder = new StringBuilder(s);
    for (int i = 0; i < s.length(); ++i) {
      char c = builder.charAt(i);
      if (Character.isUpperCase(c)) {
        c = Character.toLowerCase(c);
      } else if (Character.isLowerCase(c)) {
        c = Character.toUpperCase(c);
      }
      builder.setCharAt(i, c);
    }
    return builder.toString();
  }

}
