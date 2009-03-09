package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;


public class RAFSerializableSerializer<T extends RAFSerializable<T>>  implements RAFSerializer<T> {
  
  final RAFFactory<T> factory;
  
  public RAFSerializableSerializer(final RAFFactory<T> factory) {
    this.factory = factory;
  }

  @Override
  public void write(RandomAccessFile raf, T t) throws IOException {
    t.write(raf);
  }

  @Override
  public T read(RandomAccessFile raf) throws IOException {
    return factory.create(raf);
  }

}
