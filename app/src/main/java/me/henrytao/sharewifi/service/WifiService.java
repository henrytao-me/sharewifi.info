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

package me.henrytao.sharewifi.service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import rx.Observable;
import rx.android.content.ContentObservable;

/**
 * Created by henrytao on 4/30/15.
 */
public class WifiService {

  public static Observable<String> getAvailableWifi(Context context) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    IntentFilter intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
    Observable<Intent> observable = ContentObservable.fromBroadcast(context, intentFilter);
    wifiManager.startScan();
    return observable.flatMap(intent -> {
      StringBuilder builder = new StringBuilder();
      for (ScanResult scanResult : wifiManager.getScanResults()) {
        builder.append(scanResult.toString());
        builder.append("\n\n");
      }
      return Observable.just(builder.toString());
    });
  }
}
