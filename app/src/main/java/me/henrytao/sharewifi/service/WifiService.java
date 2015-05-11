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
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.henrytao.sharewifi.model.WifiModel;
import rx.Observable;
import rx.android.content.ContentObservable;

/**
 * Created by henrytao on 4/30/15.
 */
public class WifiService {

  public static void connectToWifi(Context context, String SSID, String password) {
    setWifiEnabled(context, true);
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    WifiConfiguration config = new WifiConfiguration();
    config.SSID = String.format("\"%s\"", SSID);
    config.preSharedKey = String.format("\"%s\"", password);
    int networkId = wifiManager.addNetwork(config);
    wifiManager.disconnect();
    wifiManager.enableNetwork(networkId, true);
    wifiManager.reconnect();
  }

  public static WifiModel getCurrentWifi(Context context) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    WifiInfo wifiInfo = wifiManager != null ? wifiManager.getConnectionInfo() : null;
    if (wifiInfo != null) {
      return new WifiModel(wifiInfo);
    }
    return null;
  }

  public static SECURITY_STATUS getSecurityStatus(ScanResult scanResult) {
    return getSecurityStatus(scanResult.capabilities);
  }

  public static SECURITY_STATUS getSecurityStatus(String capabilities) {
    if (capabilities.contains("WEP")) {
      return SECURITY_STATUS.WEP;
    } else if (capabilities.contains("PSK")) {
      return SECURITY_STATUS.PSK;
    } else if (capabilities.contains("EAP")) {
      return SECURITY_STATUS.EAP;
    }
    return SECURITY_STATUS.NONE;
  }

  public static WIFI_FREQUENCY getWifiFrequency(int frequency) {
    if (frequency > 5000) {
      return WIFI_FREQUENCY.FREQUENCY_50GHZ;
    }
    return WIFI_FREQUENCY.FREQUENCY_24GHZ;
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
      Map<String, ScanResult> map = new HashMap<>();
      if (wifiManager.getScanResults() != null) {
        for (ScanResult scanResult : wifiManager.getScanResults()) {
          if (!TextUtils.isEmpty(scanResult.SSID)) {
            if (map.get(scanResult.SSID) != null && map.get(scanResult.SSID).frequency > scanResult.frequency) {
              continue;
            }
            map.put(scanResult.SSID, scanResult);
          }
        }
        for (ScanResult scanResult : map.values()) {
          res.add(new WifiModel(scanResult));
        }
        Collections.sort(res, (lhs, rhs) -> rhs.getSignalLevel() - lhs.getSignalLevel());
      }
      return Observable.just(res);
    });
  }

  public static Observable<NetworkInfo.DetailedState> observeNetworkDetailedState(Context context) {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    Observable<Intent> observable = ContentObservable.fromBroadcast(context, intentFilter);
    return observable.flatMap(intent -> {
      NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
      return Observable.just(info.getDetailedState());
    });
  }

  public static Observable<NetworkInfo.State> observeNetworkState(Context context) {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    Observable<Intent> observable = ContentObservable.fromBroadcast(context, intentFilter);
    return observable.flatMap(intent -> {
      NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
      return Observable.just(info.getState());
    });
  }

  public static Observable<WIFI_STATE> observeWifiState(Context context) {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
    Observable<Intent> observable = ContentObservable.fromBroadcast(context, intentFilter);
    return observable.flatMap(intent -> {
      int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
      WIFI_STATE res = WIFI_STATE.UNKNOW;
      if (state == WifiManager.WIFI_STATE_ENABLED) {
        res = WIFI_STATE.ENABLED;
      } else if (state == WifiManager.WIFI_STATE_ENABLING) {
        res = WIFI_STATE.ENABLING;
      } else if (state == WifiManager.WIFI_STATE_DISABLED) {
        res = WIFI_STATE.DISABLED;
      } else if (state == WifiManager.WIFI_STATE_DISABLING) {
        res = WIFI_STATE.DISABLING;
      }
      return Observable.just(res);
    });
  }

  public static void setWifiEnabled(Context context, boolean isEnable) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    wifiManager.setWifiEnabled(isEnable);
  }

  public static void startScan(Context context) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    wifiManager.startScan();
  }

  public enum SECURITY_STATUS {
    WEP,
    PSK,
    EAP,
    NONE
  }

  public enum WIFI_FREQUENCY {
    FREQUENCY_24GHZ,
    FREQUENCY_50GHZ
  }

  public enum WIFI_STATE {
    ENABLED,
    ENABLING,
    DISABLED,
    DISABLING,
    UNKNOW
  }
}
