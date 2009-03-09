package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface RAFSerializable<T> {

  void write(final RandomAccessFile raf) throws IOException;

}
