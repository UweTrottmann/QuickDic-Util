package com.hughes.util.raf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;


public class SerializableSerializer<T>  implements RAFSerializer<T> {

  @Override
  public void write(RandomAccessFile raf, T t) throws IOException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(t);
    oos.close();
    final byte[] bytes = baos.toByteArray();
    raf.writeInt(bytes.length);
    raf.write(bytes);
  }

  @SuppressWarnings("unchecked")
  @Override
  public T read(RandomAccessFile raf) throws IOException {
    final int length = raf.readInt();
    final byte[] bytes = new byte[length];
    raf.read(bytes);
    final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    final ObjectInputStream ois = new ObjectInputStream(bais);
    Serializable result;
    try {
      result = (Serializable) ois.readObject();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    ois.close();
    return (T) result;
  }

}
