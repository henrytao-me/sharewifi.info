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

/**
 * Created by henrytao on 4/18/15.
 */
public class StringUtils {

  public static boolean isEmpty(CharSequence text, boolean isTrim) {
    if (text != null && isTrim) {
      text = text.toString().trim();
    }
    if (text != null && !text.toString().isEmpty()) {
      return false;
    }
    return true;
  }

  public static boolean isEmpty(CharSequence text) {
    return isEmpty(text, true);
  }

}
