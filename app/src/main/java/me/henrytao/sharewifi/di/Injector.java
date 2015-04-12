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

package me.henrytao.sharewifi.di;

import dagger.ObjectGraph;

/**
 * Created by henrytao on 4/12/15.
 */
public class Injector {

  private static ObjectGraph mObjectGraph;

  public static void push(Object... modules) {
    if (mObjectGraph == null) {
      mObjectGraph = ObjectGraph.create(modules);
    } else {
      mObjectGraph = mObjectGraph.plus(modules);
    }
  }

  public static void inject(Object target) {
    if (mObjectGraph != null) {
      mObjectGraph.inject(target);
    }
  }

}
