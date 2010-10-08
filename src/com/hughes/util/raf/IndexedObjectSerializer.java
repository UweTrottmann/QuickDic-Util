package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import com.hughes.util.IndexedObject;

public class IndexedObjectSerializer<T extends IndexedObject> implements RAFSerializer<T> {
  
  private final List<T> list;
  
  public IndexedObjectSerializer(final List<T> list) {
    this.list = list;
  }

  @Override
  public T read(RandomAccessFile raf) throws IOException {
    final int index = raf.readInt();
    return list.get(index);
  }

  @Override
  public void write(RandomAccessFile raf, T t) throws IOException {
    raf.writeInt(t.index());
  }

}
