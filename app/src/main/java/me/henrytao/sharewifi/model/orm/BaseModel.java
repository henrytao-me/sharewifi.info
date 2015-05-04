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

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.henrytao.sharewifi.util.JsonUtils;

/**
 * Created by henrytao on 3/31/15.
 */
public abstract class BaseModel<T extends BaseModel> {

  private static final Map<Class, Deserializer> deserializerMap;

  private static final Map<Class, Serializer> serializerMap;

  static {
    deserializerMap = new HashMap<>();
    deserializerMap.put(Date.class, new DateAdapter());

    serializerMap = new HashMap<>();
    serializerMap.put(Date.class, new DateAdapter());
  }

  private Field[] getDeclaredFields() {
    List<Field> fields = new ArrayList<Field>();
    fields.addAll(Arrays.asList(getClass().getDeclaredFields()));
    fields.addAll(Arrays.asList(getClass().getSuperclass().getDeclaredFields()));
    return fields.toArray(new Field[fields.size()]);
  }

  public Map<String, Object> serialize() throws SerializerException {
    try {
      Map<String, Object> map = new HashMap<>();
      Field[] fields = getDeclaredFields();
      for (Field f : fields) {
        if (!f.isAnnotationPresent(Column.class)) {
          continue;
        }
        Column column = f.getAnnotation(Column.class);
        if (!column.serialize()) {
          continue;
        }
        f.setAccessible(true);
        String name = column.name();
        Object value = f.get(this);
        Class type = f.getType();
        Serializer serializer = serializerMap.get(type);
        if (column.notNull() && value == null) {
          throw new SerializerException("Field <" + name + "> is required but null");
        } else if (value == null) {
          continue;
        } else if (serializer != null) {
          map.put(name, serializer.serialize(value));
        } else if (BaseModel.class.isAssignableFrom(type)) {
          map.put(name, ((BaseModel) value).serialize());
        } else {
          map.put(name, value);
        }
      }
      return map;
    } catch (IllegalAccessException e) {
      throw new SerializerException(e.getMessage());
    }
  }

  public T deserialize(Map<String, Object> map) throws DeserializerException {
    try {
      Field[] fields = getDeclaredFields();
      for (Field f : fields) {
        if (!f.isAnnotationPresent(Column.class)) {
          continue;
        }
        Column column = f.getAnnotation(Column.class);
        if (!column.deserialize()) {
          continue;
        }
        f.setAccessible(true);
        String name = column.name();
        Object value = map.get(name);
        Class type = f.getType();
        Deserializer deserializer = deserializerMap.get(type);
        if (column.notNull() && value == null) {
          throw new DeserializerException("Field <" + name + "> is required but null");
        } else if (deserializer != null) {
          f.set(this, deserializer.deserialize(value));
        } else if (BaseModel.class.isAssignableFrom(type)) {
          BaseModel model = (BaseModel) type.newInstance();
          if (value instanceof JSONObject) {
            model.deserialize(JsonUtils.encode((JSONObject) value));
          } else if (value instanceof Map) {
            model.deserialize((Map) value);
          } else {
            throw new DeserializerException("Field <" + name + "> does not have correct input data");
          }
          f.set(this, model);
        } else if (boolean.class.isAssignableFrom(type)) {
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
    } catch (IllegalAccessException e) {
      throw new DeserializerException(e.getMessage());
    } catch (InstantiationException e) {
      throw new DeserializerException(e.getMessage());
    }
    return (T) this;
  }

  public T deserialize(String json) throws DeserializerException {
    return deserialize(JsonUtils.decode(json));
  }

  public interface Fields {

    final String ID = "id";
  }

  @Column(name = Fields.ID)
  protected String mId;

  public String getId() {
    return mId;
  }

}
