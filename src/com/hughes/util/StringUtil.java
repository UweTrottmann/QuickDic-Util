package com.hughes.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

  public static String longestCommonSubstring(final String s1, final String s2) {
    for (int i = 0; i < s1.length() && i < s2.length(); i++) {
      if (s1.charAt(i) != s2.charAt(i)) {
        return s1.substring(0, i);
      }
    }
    return s1.length() < s2.length() ? s1 : s2;
  }

  public static String remove(final StringBuilder s, final Pattern start,
      final Pattern end, final boolean includeEnd) {
    final Matcher startMatcher = start.matcher(s);
    if (!startMatcher.find()) {
      return null;
    }
    final int startIndex = startMatcher.start();
    
    final Matcher endMatcher = end.matcher(s);
    endMatcher.region(startMatcher.end(), s.length());
    final int endIndex; 
    if (endMatcher.find()) {
      endIndex = includeEnd ? endMatcher.end() : endMatcher.start();
    } else {
      endIndex = s.length();
    }
    
    final String result = s.substring(startIndex, endIndex);
    s.replace(startIndex, endIndex, "");
    return result;
  }
  
  public static StringBuilder removeAll(final StringBuilder s, final Pattern start,
      final Pattern end) {
    while (remove(s, start, end, true) != null);
    return s;
  }
}
