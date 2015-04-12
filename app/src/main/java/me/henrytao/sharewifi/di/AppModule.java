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

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.henrytao.sharewifi.App;
import me.henrytao.sharewifi.activity.MainActivity;

/**
 * Created by henrytao on 4/12/15.
 */
@Module(
    injects = {
        MainActivity.class
    },
    includes = {
        DomainModule.class
    }
)
public class AppModule {

  private App mApp;

  public AppModule(App app) {
    mApp = app;
  }

  @Provides
  @Singleton
  public Context provideApplicationContext() {
    return mApp;
  }
}
