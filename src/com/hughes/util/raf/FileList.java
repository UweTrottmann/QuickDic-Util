package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.AbstractList;
import java.util.Collection;


public class FileList<T> extends AbstractList<T> {

  private static final int LONG_BYTES = Long.SIZE / 8;

  final RandomAccessFile raf;
  final RAFSerializer<T> serializer;
  final long tocOffset;
  final int size;

  public FileList(final RandomAccessFile raf,
      final RAFSerializer<T> serializer, final long startOffset)
      throws IOException {
    this.raf = raf;
    this.serializer = serializer;
    raf.seek(startOffset);
    size = raf.readInt();
    this.tocOffset = raf.getFilePointer();
    raf.seek(getEndOffset());
  }
  
  public long getEndOffset() {
    try {
      raf.seek(tocOffset + size * LONG_BYTES);
      return raf.readLong();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public T get(final int i) {
    if (i < 0 || i >= size) {
      throw new IndexOutOfBoundsException("" + i);
    }
    try {
      raf.seek(tocOffset + i * LONG_BYTES);
      final long start = raf.readLong();
      raf.seek(start);
      return serializer.read(raf);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int size() {
    return size;
  }
  
  public static <T> FileList<T> create(final RandomAccessFile raf,
      final RAFSerializer<T> serializer, final long startOffset) throws IOException {
    return new FileList<T>(raf, serializer, startOffset);
  }

  public static <T> void write(final RandomAccessFile raf,
      final Collection<T> list, final RAFSerializer<T> serializer)
      throws IOException {
    raf.writeInt(list.size());
    long tocPos = raf.getFilePointer();
    raf.seek(tocPos + LONG_BYTES * (list.size() + 1));
    int i = 0;
    final long dataStart = raf.getFilePointer();
    for (final T t : list) {
      final long startOffset = raf.getFilePointer();
      raf.seek(tocPos);
      raf.writeLong(startOffset);
      tocPos = raf.getFilePointer();
      raf.seek(startOffset);
      serializer.write(raf, t);
      ++i;
    }
    final long endOffset = raf.getFilePointer();
    raf.seek(tocPos);
    raf.writeLong(endOffset);
    assert dataStart == raf.getFilePointer();
    raf.seek(endOffset);
  }

}
