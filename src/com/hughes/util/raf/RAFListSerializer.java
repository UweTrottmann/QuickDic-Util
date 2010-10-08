package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface RAFListSerializer<T> {

  public void write(final RandomAccessFile raf, final T t) throws IOException;

  public T read(final RandomAccessFile raf, final int readIndex) throws IOException;
    
  static final class Wrapper<T> implements RAFListSerializer<T> {
    
    private final RAFSerializer<T> serializer;

    public Wrapper(final RAFSerializer<T> serializer) {
      this.serializer = serializer;
    }

    @Override
    public T read(RandomAccessFile raf, int readIndex) throws IOException {
      return serializer.read(raf);
    }

    @Override
    public void write(RandomAccessFile raf, T t) throws IOException {
      serializer.write(raf, t);
    }

  }

}
