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

import java.util.Date;

/**
 * Created by henrytao on 4/3/15.
 */
public class DateAdapter implements TypeAdapter<Date> {

  @Override
  public Date deserializer(Object value) {
    long timestamp;
    if (value instanceof Double) {
      timestamp = ((Double) value).longValue();
    } else if (value instanceof Integer) {
      timestamp = ((Integer) value).longValue();
    } else if (value instanceof Long) {
      timestamp = (Long) value;
    } else {
      timestamp = 0;
    }
    return timestamp > 0 ? new Date(timestamp) : null;
  }
}
