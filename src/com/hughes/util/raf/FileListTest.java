package com.hughes.util.raf;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import junit.framework.TestCase;

public class FileListTest extends TestCase {

  public void testFileListString() throws IOException {
    testFileList(RAFSerializer.STRING);
  }

  public void testFileListSerializable() throws IOException {
    testFileList(new SerializableSerializer<String>());
  }

  public void testFileList(final RAFSerializer<String> serializer) throws IOException {
    final File file = File.createTempFile("asdf", "asdf");
    file.deleteOnExit();
    final RandomAccessFile raf = new RandomAccessFile(file, "rw");

    raf.writeUTF("Hello World!");
    final List<String> l1 = Arrays.asList("1a", "1bc", "1def");
    final List<String> l2 = Arrays.asList("2aa", "2abc", "2adef");
    FileList.write(raf, l1, serializer);
    FileList.write(raf, l2, serializer);
    raf.writeUTF("Goodbye World!");

    raf.seek(0);
    assertEquals("Hello World!", raf.readUTF());
    final FileList<String> l1Copy = new FileList<String>(raf,
        serializer, raf.getFilePointer());
    assertEquals(l1, l1Copy);
    final FileList<String> l2Copy = new FileList<String>(raf,
        serializer, l1Copy.getEndOffset());
    assertEquals(l2, l2Copy);
    raf.seek(l2Copy.getEndOffset());
    assertEquals("Goodbye World!", raf.readUTF());
  }

  public void testEmptyList() throws IOException {
    final File file = File.createTempFile("asdf", "asdf");
    file.deleteOnExit();
    final RandomAccessFile raf = new RandomAccessFile(file, "rw");

    raf.writeUTF("Hello World!");
    final List<String> l1 = Collections.emptyList();
    FileList.write(raf, l1, RAFSerializer.STRING);
    raf.writeUTF("Goodbye World!");

    raf.seek(0);
    assertEquals("Hello World!", raf.readUTF());
    final FileList<String> l1Copy = new FileList<String>(raf,
        RAFSerializer.STRING, raf.getFilePointer());
    assertEquals(l1, l1Copy);
    raf.seek(l1Copy.getEndOffset());
    assertEquals("Goodbye World!", raf.readUTF());
  }

}
