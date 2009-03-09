package com.hughes.util.raf;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface RAFFactory<T> {
  
  T create(final RandomAccessFile raf) throws IOException;

}
