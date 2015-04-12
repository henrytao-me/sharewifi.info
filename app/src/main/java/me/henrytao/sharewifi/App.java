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

package me.henrytao.sharewifi;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import me.henrytao.sharewifi.di.AnalyticsManager;
import me.henrytao.sharewifi.di.AppModule;
import me.henrytao.sharewifi.di.DomainModule;
import me.henrytao.sharewifi.di.Injector;

/**
 * Created by henrytao on 4/12/15.
 */
public class App extends Application {

  private ObjectGraph mObjectGraph;

  @Override
  public void onCreate() {
    super.onCreate();
    Injector.push(new AppModule(this));
  }

}
