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
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.henrytao.sharewifi.model.WifiModel;
import rx.Observable;
import rx.android.content.ContentObservable;

/**
 * Created by henrytao on 4/30/15.
 */
public class WifiService {

  public static WifiModel getCurrentWifi(Context context) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    WifiInfo wifiInfo = wifiManager != null ? wifiManager.getConnectionInfo() : null;
    if (wifiInfo != null) {
      return new WifiModel(wifiInfo);
    }
    return null;
  }

  public static boolean isCurrentWifi(Context context, WifiModel wifiModel) {
    WifiModel currentWifi = getCurrentWifi(context);
    return currentWifi != null &&
        wifiModel != null &&
        TextUtils.equals(currentWifi.getSSID(), wifiModel.getSSID()) &&
        TextUtils.equals(currentWifi.getBSSID(), wifiModel.getBSSID());
  }

  public static boolean isWifiEnabled(Context context) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    return wifiManager.isWifiEnabled();
  }

  public static Observable<List<WifiModel>> observeAvailableWifiList(Context context) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
    intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
    intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
    Observable<Intent> observable = ContentObservable.fromBroadcast(context, intentFilter);
    wifiManager.startScan();
    return observable.flatMap(intent -> {
      List<WifiModel> res = new ArrayList<>();
      for (ScanResult scanResult : wifiManager.getScanResults()) {
        res.add(new WifiModel(scanResult));
      }
      return Observable.just(res);
    });
  }

  public static Observable<Integer> observeWifiEnabled(Context context) {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
    Observable<Intent> observable = ContentObservable.fromBroadcast(context, intentFilter);
    return observable.flatMap(intent -> Observable.just(intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1)));
  }

  public static void setWifiEnabled(Context context, boolean isEnable) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    wifiManager.setWifiEnabled(isEnable);
  }

}
