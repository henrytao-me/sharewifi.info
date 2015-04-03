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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by henrytao on 3/31/15.
 */
public abstract class BaseEntity<T extends BaseEntity> {

  private static final Map<Class, Deserializer> deserializerMap;

  static {
    deserializerMap = new HashMap<>();
    deserializerMap.put(Date.class, new DateAdapter());
  }

  @Column(name = BaseEntityColumns.ID)
  protected String mId;

  public String getId() {
    return mId;
  }

  private Field[] getDeclaredFields() {
    List<Field> fields = new ArrayList<Field>();
    fields.addAll(Arrays.asList(getClass().getDeclaredFields()));
    fields.addAll(Arrays.asList(getClass().getSuperclass().getDeclaredFields()));
    return fields.toArray(new Field[fields.size()]);
  }

  public T deserialize(Map<String, Object> map) throws IllegalAccessException {
    Field[] fields = getDeclaredFields();
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
        }
      }
    }
    return (T) this;
  }

  public interface BaseEntityColumns {

    final String ID = "id";
  }

}
