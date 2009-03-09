package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface RAFSerializer<T> {

  public void write(final RandomAccessFile raf, final T t) throws IOException;

  public T read(final RandomAccessFile raf) throws IOException;

  public static final RAFSerializer<String> STRING = new RAFSerializer<String>() {
    @Override
    public void write(RandomAccessFile raf, String t) throws IOException {
      raf.writeUTF(t);
    }

    @Override
    public String read(RandomAccessFile raf) throws IOException {
      return raf.readUTF();
    }
  };

}
