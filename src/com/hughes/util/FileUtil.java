package com.hughes.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {
  
  public static void writeObject(final Serializable o, final File file) throws FileNotFoundException, IOException {
    final ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
    oos.writeObject(o);
    oos.close();
  }

  public static void writeObject(final Serializable o, final String file) throws FileNotFoundException, IOException {
    writeObject(o, new File(file));
  }

  public static Object readObject(final File file) throws IOException, ClassNotFoundException {
    final ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
    final Object result = ois.readObject();
    ois.close();
    return result;
  }

  public static Object readObject(final String file) throws IOException, ClassNotFoundException {
    return readObject(new File(file));
  }
  
  public static <T> T readObject(final File file, final Class<T> class1) throws IOException, ClassNotFoundException {
    return class1.cast(readObject(file));
  }
  
  public static String readLine(final RandomAccessFile file, final long startPos) throws IOException {
    file.seek(startPos);
    return file.readLine();
  }
  
  public static List<String> readLines(final File file) throws IOException {
    final List<String> result = new ArrayList<String>();
    final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    String line;
    while ((line = in.readLine()) != null) {
      result.add(line);
    }
    in.close();
    return result;
  }
  
  public static String readToString(final File file) throws IOException {
    final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    StringBuilder result = new StringBuilder();
    String line;
    while ((line = in.readLine()) != null) {
      result.append(line).append("\n");
    }
    in.close();
    return result.toString();
  }

}
