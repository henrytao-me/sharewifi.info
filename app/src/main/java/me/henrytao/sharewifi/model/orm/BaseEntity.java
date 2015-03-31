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

package me.henrytao.sharewifi.model.orm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * Created by henrytao on 3/31/15.
 */
public abstract class BaseEntity<T extends BaseEntity> {

  @Column(name = BaseEntityColumns.INTERNAL_ID, serialize = false)
  private long mInternalID;

  @Column(name = BaseEntityColumns.ID)
  private String mId;

  public T deserialize(Map<String, Object> map) throws IllegalAccessException {
    Field[] fields = getClass().getDeclaredFields();
    for (Field f : fields) {
      if (f.isAnnotationPresent(Column.class)) {
        f.setAccessible(true);
        Column column = f.getAnnotation(Column.class);
        if (!column.deserialize()) {
          continue;
        }
        String name = column.name();
        Object value = map.get(name);
        Class type = f.getType();
        if (boolean.class.isAssignableFrom(type)) {
          f.setBoolean(this, value == null ? false : (Boolean) value);
        } else if (double.class.isAssignableFrom(type)) {
          f.setDouble(this, value == null ? 0.0 : (Double) value);
        } else if (float.class.isAssignableFrom(type)) {
          f.setFloat(this, value == null ? 0f : (Float) value);
        } else if (int.class.isAssignableFrom(type)) {
          f.setInt(this, value == null ? 0 : (int) value);
        } else if (short.class.isAssignableFrom(type)) {
          f.setShort(this, value == null ? 0 : (short) value);
        } else if (byte.class.isAssignableFrom(type)) {
          f.setByte(this, value == null ? 0 : (byte) value);
        } else if (String.class.isAssignableFrom(type)) {
          f.set(this, value);
        } else if (BaseEntity.class.isAssignableFrom(type)) {
          //try {
          //  T obj = ((T) type.newInstance()).deserialize(value);
          //} catch (InstantiationException e) {
          //  e.printStackTrace();
          //}
          //type.newInstance();
          //Constructor<?> ctr = type.getConstructor();
          //Object obj = ctr.newInstance(new Object[]{});
          //f.set(this, type.getConstructor(String.class).newInstance());
        }
      }
    }
    return (T) this;
  }

  public interface BaseEntityColumns {

    final String INTERNAL_ID = "internal_id";
    final String ID = "id";
  }

}
