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

package me.henrytao.sharewifi.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by henrytao on 5/3/15.
 */
public class JsonUtils {

  public static Map<String, Object> decode(String jsonString) {
    try {
      Map<String, Object> map = new HashMap<>();
      JSONObject jsonObject = new JSONObject(jsonString);
      Iterator<?> keys = jsonObject.keys();
      while (keys.hasNext()) {
        String key = (String) keys.next();
        Object value = jsonObject.get(key);
        map.put(key, value);
      }
      return map;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String encode(Map<String, Object> json) {
    return (new JSONObject(json)).toString();
  }

}
