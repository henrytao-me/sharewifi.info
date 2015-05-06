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

import me.henrytao.sharewifi.model.orm.BaseModel;
import me.henrytao.sharewifi.model.orm.Column;

/**
 * Created by henrytao on 5/1/15.
 */
public class WifiModel extends BaseModel<WifiModel> {

  public final static int SIGNAL_LEVEL = 5; // Level: 0, 1, 2, 3, 4

  @Column(name = Fields.ADDRESS)
  private String mAddress;

  @Column(name = Fields.BSSID)
  private String mBSSID;

  @Column(name = Fields.CAPABILITIES)
  private String mCapabilities;

  @Column(name = Fields.FREQUENCY)
  private int mFrequency;

  @Column(name = Fields.MAC_ADDRESS)
  private String mMacAddress;

  @Column(name = Fields.NAME)
  private String mName;

  @Column(name = Fields.PASSWORD)
  private String mPasswrod;

  @Column(name = Fields.SSID)
  private String mSSID;

  private int mSignalLevel;

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

  public String getAddress() {
    return mAddress;
  }

  public void setAddress(String address) {
    mAddress = address;
  }

  public String getBSSID() {
    return mBSSID;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public String getPasswrod() {
    return mPasswrod;
  }

  public void setPasswrod(String passwrod) {
    mPasswrod = passwrod;
  }

  public String getSSID() {
    return mSSID;
  }

  public int getSignalLevel() {
    return mSignalLevel;
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
