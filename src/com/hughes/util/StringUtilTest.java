package com.hughes.util;

import java.util.regex.Pattern;

import junit.framework.TestCase;

public class StringUtilTest extends TestCase {
  
  public void testRemove() {
    StringBuilder sb;
    
    sb = new StringBuilder("a<!--asdfasdf-->b<!---> -->c<!---->d");
    assertEquals("<!--asdfasdf-->", StringUtil.remove(sb, Pattern.compile("<!--", Pattern.LITERAL), Pattern.compile("-->", Pattern.LITERAL), true));
    assertEquals("ab<!---> -->c<!---->d", sb.toString());

    assertEquals("<!---> -->", StringUtil.remove(sb, Pattern.compile("<!--", Pattern.LITERAL), Pattern.compile("-->", Pattern.LITERAL), true));
    assertEquals("<!---->", StringUtil.remove(sb, Pattern.compile("<!--", Pattern.LITERAL), Pattern.compile("-->", Pattern.LITERAL), true));

    sb = new StringBuilder("a<!--asdfasdf-->b<!---> -->c<!---->d");
    assertEquals("abcd", StringUtil.removeAll(sb, Pattern.compile("<!--", Pattern.LITERAL), Pattern.compile("-->", Pattern.LITERAL)).toString());

  }

}
