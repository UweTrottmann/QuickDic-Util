package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.AbstractList;
import java.util.Collection;

public class UniformFileList<T> extends AbstractList<T> {

  final RandomAccessFile raf;
  final RAFSerializer<T> serializer;
  final int size;
  final int datumSize;
  final long dataStart;
  final long endOffset;

  public UniformFileList(final RandomAccessFile raf,
      final RAFSerializer<T> serializer, final long startOffset)
      throws IOException {
    synchronized (raf) {
      this.raf = raf;
      this.serializer = serializer;
      raf.seek(startOffset);

      size = raf.readInt();
      datumSize = raf.readInt();
      dataStart = raf.getFilePointer();
      endOffset = dataStart + size * datumSize;
      raf.seek(endOffset);
    }
  }

  public long getEndOffset() {
    return endOffset;
  }

  @Override
  public T get(final int i) {
    if (i < 0 || i >= size) {
      throw new IndexOutOfBoundsException("" + i);
    }
    try {
      synchronized (raf) {
        raf.seek(dataStart + i * datumSize);
        final T result = serializer.read(raf);
        if (raf.getFilePointer() != dataStart + (i + 1) * datumSize) {
          throw new RuntimeException("Read "
              + (raf.getFilePointer() - (dataStart + i * datumSize))
              + " bytes, should have read " + datumSize);
        }
        return result;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int size() {
    return size;
  }

  public static <T> UniformFileList<T> create(final RandomAccessFile raf,
      final RAFSerializer<T> serializer, final long startOffset)
      throws IOException {
    return new UniformFileList<T>(raf, serializer, startOffset);
  }

  public static <T> void write(final RandomAccessFile raf,
      final Collection<T> list, final RAFSerializer<T> serializer,
      final int datumSize) throws IOException {
    raf.writeInt(list.size());
    raf.writeInt(datumSize);
    for (final T t : list) {
      final long startOffset = raf.getFilePointer();
      serializer.write(raf, t);
      if (raf.getFilePointer() != startOffset + datumSize) {
        throw new RuntimeException("Wrote "
            + (raf.getFilePointer() - startOffset)
            + " bytes, should have written " + datumSize);
      }
    }
  }

}
