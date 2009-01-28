package com.hughes.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class FileUtil {
  
  public static void write(final Serializable o, final File file) throws FileNotFoundException, IOException {
    final ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
    oos.writeObject(o);
    oos.close();
  }

  public static void write(final Serializable o, final String file) throws FileNotFoundException, IOException {
    write(o, new File(file));
  }

  public static Object read(final File file) throws IOException, ClassNotFoundException {
    final ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
    final Object result = ois.readObject();
    ois.close();
    return result;
  }

  public static Object read(final String file) throws IOException, ClassNotFoundException {
    return read(new File(file));
  }

}
