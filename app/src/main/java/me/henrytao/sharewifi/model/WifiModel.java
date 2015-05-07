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

package me.henrytao.sharewifi.model;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.model.orm.BaseModel;
import me.henrytao.sharewifi.model.orm.Column;
import me.henrytao.sharewifi.service.WifiService;

/**
 * Created by henrytao on 5/1/15.
 */
@Accessors(prefix = "m")
public class WifiModel extends BaseModel<WifiModel> {

  public final static int SIGNAL_LEVEL = 5; // Level: 0, 1, 2, 3, 4

  private static final int[][] R_SIGNAL_LEVEL = {{
      R.drawable.ic_signal_wifi_0_bar,
      R.drawable.ic_signal_wifi_1_bar,
      R.drawable.ic_signal_wifi_2_bar,
      R.drawable.ic_signal_wifi_3_bar,
      R.drawable.ic_signal_wifi_4_bar
  }, {
      R.drawable.ic_signal_wifi_0_bar_lock,
      R.drawable.ic_signal_wifi_1_bar_lock,
      R.drawable.ic_signal_wifi_2_bar_lock,
      R.drawable.ic_signal_wifi_3_bar_lock,
      R.drawable.ic_signal_wifi_4_bar_lock
  }, {
      R.drawable.ic_signal_wifi_0_bar_unlock,
      R.drawable.ic_signal_wifi_1_bar_unlock,
      R.drawable.ic_signal_wifi_2_bar_unlock,
      R.drawable.ic_signal_wifi_3_bar_unlock,
      R.drawable.ic_signal_wifi_4_bar_unlock
  }};

  @Column(name = Fields.ADDRESS)
  @Getter @Setter private String mAddress;

  @Column(name = Fields.BSSID)
  @Getter @Setter private String mBSSID;

  @Column(name = Fields.CAPABILITIES)
  @Getter @Setter private String mCapabilities;

  @Column(name = Fields.FREQUENCY)
  @Getter @Setter private int mFrequency;

  @Column(name = Fields.MAC_ADDRESS)
  @Getter @Setter private String mMacAddress;

  @Column(name = Fields.NAME)
  @Getter @Setter private String mName;

  @Column(name = Fields.PASSWORD)
  @Getter @Setter private String mPasswrod;

  @Column(name = Fields.SSID)
  @Getter @Setter private String mSSID;

  @Getter @Setter private int mSignalLevel;

  public WifiModel() {

  }

  public WifiModel(String SSID, String BSSID, String capabilities, int frequency, int RSSI, String macAddress) {
    mSSID = SSID;
    mBSSID = BSSID;
    mCapabilities = capabilities;
    mFrequency = frequency;
    mMacAddress = macAddress;
    mSignalLevel = WifiManager.calculateSignalLevel(RSSI, SIGNAL_LEVEL);
  }

  public WifiModel(ScanResult scanResult) {
    this(scanResult.SSID, scanResult.BSSID, scanResult.capabilities, scanResult.frequency, scanResult.level, null);
  }

  public WifiModel(WifiInfo wifiInfo) {
    this(wifiInfo.getSSID().replaceFirst("^\"", "").replaceFirst("\"$", ""),
        wifiInfo.getBSSID(), null, 0, wifiInfo.getRssi(), wifiInfo.getMacAddress());
  }

  public int getSignalLevelResource() {
    int type = isPasswordRequired() ? (hasPassword() ? 2 : 1) : 0;
    int signalLevel = getSignalLevel();
    if (signalLevel < 0) {
      signalLevel = 0;
    } else if (signalLevel > R_SIGNAL_LEVEL[type].length - 1) {
      signalLevel = R_SIGNAL_LEVEL[type].length - 1;
    }
    return R_SIGNAL_LEVEL[type][signalLevel];
  }

  public WifiService.WIFI_FREQUENCY getWifiFrequency() {
    return WifiService.getWifiFrequency(getFrequency());
  }

  public boolean hasPassword() {
    return !TextUtils.isEmpty(getPasswrod());
  }

  public boolean isPasswordRequired() {
    return WifiService.getSecurityStatus(getCapabilities()) != WifiService.SECURITY_STATUS.NONE;
  }

  public interface Fields extends BaseModel.Fields {

    final String ADDRESS = "address";
    final String BSSID = "bssid";
    final String CAPABILITIES = "capabilities";
    final String FREQUENCY = "frequency";
    final String MAC_ADDRESS = "mac_address";
    final String NAME = "name";
    final String PASSWORD = "password";
    final String SSID = "ssid";
  }

}
