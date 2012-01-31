// Copyright 2011 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.AbstractList;
import java.util.Collection;
import java.util.RandomAccess;

public class RAFList<T> extends AbstractList<T> implements RandomAccess {

  private static final int LONG_BYTES = Long.SIZE / 8;

  final RandomAccessFile raf;
  final RAFListSerializer<T> serializer;
  final long tocOffset;
  final int size;
  final long endOffset;

  public RAFList(final RandomAccessFile raf,
      final RAFListSerializer<T> serializer, final long startOffset)
      throws IOException {
    synchronized (raf) {
      this.raf = raf;
      this.serializer = serializer;
      raf.seek(startOffset);
      size = raf.readInt();
      this.tocOffset = raf.getFilePointer();

      raf.seek(tocOffset + size * LONG_BYTES);
      endOffset = raf.readLong();
      raf.seek(endOffset);
    }
  }

  public long getEndOffset() {
    return endOffset;
  }

  @Override
  public T get(final int i) {
    if (i < 0 || i >= size) {
      throw new IndexOutOfBoundsException(i + ", size=" + size);
    }
    try {
      synchronized (raf) {
        raf.seek(tocOffset + i * LONG_BYTES);
        final long start = raf.readLong();
        raf.seek(start);
        return serializer.read(raf, i);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int size() {
    return size;
  }

  public static <T> RAFList<T> create(final RandomAccessFile raf,
      final RAFListSerializer<T> serializer, final long startOffset)
      throws IOException {
    return new RAFList<T>(raf, serializer, startOffset);
  }

  /**
   * Same, but deserialization ignores indices.
   */
  public static <T> RAFList<T> create(final RandomAccessFile raf,
      final RAFSerializer<T> serializer, final long startOffset)
      throws IOException {
    return new RAFList<T>(raf, getWrapper(serializer), startOffset);
  }

  public static <T> void write(final RandomAccessFile raf,
      final Collection<T> list, final RAFListSerializer<T> serializer)
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

  public static <T> void write(final RandomAccessFile raf,
      final Collection<T> list, final RAFSerializer<T> serializer)
      throws IOException {
    write(raf, list, getWrapper(serializer));
  }
  
  public static <T> RAFListSerializer<T> getWrapper(final RAFSerializer<T> serializer) {
    return new RAFListSerializer.Wrapper<T>(serializer);
  }


}
