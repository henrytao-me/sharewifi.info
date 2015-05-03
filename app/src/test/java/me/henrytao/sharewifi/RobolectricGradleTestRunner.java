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

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

import me.henrytao.sharewifi.BuildConfig;

/**
 * Created by henrytao on 5/1/15.
 */
public class RobolectricGradleTestRunner extends RobolectricTestRunner {

  public RobolectricGradleTestRunner(final Class<?> testClass) throws InitializationError {
    super(testClass);
    String buildVariant = (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR + "/") + BuildConfig.BUILD_TYPE;
    String intermediatesPath = BuildConfig.class.getResource("").toString().replace("file:", "");
    intermediatesPath = intermediatesPath.substring(0, intermediatesPath.indexOf("/classes"));

    System.setProperty("android.package", BuildConfig.APPLICATION_ID);
    System.setProperty("android.manifest", intermediatesPath + "/manifests/full/" + buildVariant + "/AndroidManifest.xml");
    System.setProperty("android.resources", intermediatesPath + "/res/" + buildVariant);
    System.setProperty("android.assets", intermediatesPath + "/assets/" + buildVariant);
  }
}
