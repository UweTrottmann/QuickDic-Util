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


public class RAFSerializableSerializer<T extends RAFSerializable<T>>  implements RAFSerializer<T> {
  
  final RAFFactory<T> factory;
  
  public RAFSerializableSerializer(final RAFFactory<T> factory) {
    this.factory = factory;
  }

  @Override
  public void write(RandomAccessFile raf, T t) throws IOException {
    t.write(raf);
  }

  @Override
  public T read(RandomAccessFile raf) throws IOException {
    return factory.create(raf);
  }

}
