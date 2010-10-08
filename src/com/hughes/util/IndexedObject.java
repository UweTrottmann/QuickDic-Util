package com.hughes.util;

/**
 * An object that knows its position (index) inside a list.
 */
public class IndexedObject {
  
  protected int index;
  
  public IndexedObject(final int index) {
    this.index = index;
  }
  
  public int index() {
    return index;
  }

}
