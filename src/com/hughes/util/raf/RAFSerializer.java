package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

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

  // Serializes any class with a write method and the proper constructor. 
  public static final class RAFSerializableSerializer<T extends RAFSerializable<T>> implements RAFSerializer<T> {
    private final Constructor<T> constructor;
    
    public RAFSerializableSerializer(final Class<T> clazz) {
      try {
        this.constructor = clazz.getConstructor(RandomAccessFile.class);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    
    @Override
    public void write(RandomAccessFile raf, T t) throws IOException {
      t.write(raf);
    }

    @Override
    public T read(RandomAccessFile raf) throws IOException {
      try {
        return constructor.newInstance(raf);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  };

}
