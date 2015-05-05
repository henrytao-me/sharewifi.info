/*
 * Copyright 2015 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.henrytao.sharewifi.model.orm.benchmark;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by henrytao on 5/5/15.
 */
public class SerializableModel implements Serializable {

  private String mName;

  private int mAge;

  private long mCreatedAt;

  public SerializableModel(String name, int age, long createdAt) {
    mName = name;
    mAge = age;
    mCreatedAt = createdAt;
  }

  public String getName() {
    return mName;
  }

  public int getAge() {
    return mAge;
  }

  public long getCreatedAt() {
    return mCreatedAt;
  }
}
