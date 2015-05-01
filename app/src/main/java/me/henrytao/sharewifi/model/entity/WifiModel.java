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

package me.henrytao.sharewifi.model.entity;

import android.net.wifi.ScanResult;

import me.henrytao.sharewifi.model.orm.BaseModel;
import me.henrytao.sharewifi.model.orm.Column;

/**
 * Created by henrytao on 5/1/15.
 */
public class WifiModel extends BaseModel<WifiModel> {

  @Column(name = Fields.SSID)
  private String mSSID;

  @Column(name = Fields.BSSID)
  private String mBSSID;

  @Column(name = Fields.CAPABILITIES)
  private String mCapabilities;

  @Column(name = Fields.FREQUENCY)
  private int mFrequency;

  @Column(name = Fields.NAME)
  private String mName;

  @Column(name = Fields.ADDRESS)
  private String mAddress;

  @Column(name = Fields.PASSWORD)
  private String mPasswrod;

  int mSignalLevel;

  public WifiModel(ScanResult scanResult, int signalLevel) {
    mSSID = scanResult.SSID;
    mBSSID = scanResult.BSSID;
    mCapabilities = scanResult.capabilities;
    mFrequency = scanResult.frequency;
    mSignalLevel = signalLevel;
  }

  public String getSSID() {
    return mSSID;
  }

  public void setSSID(String SSID) {
    mSSID = SSID;
  }

  public String getBSSID() {
    return mBSSID;
  }

  public void setBSSID(String BSSID) {
    mBSSID = BSSID;
  }

  public String getCapabilities() {
    return mCapabilities;
  }

  public void setCapabilities(String capabilities) {
    mCapabilities = capabilities;
  }

  public int getFrequency() {
    return mFrequency;
  }

  public void setFrequency(int frequency) {
    mFrequency = frequency;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public String getAddress() {
    return mAddress;
  }

  public void setAddress(String address) {
    mAddress = address;
  }

  public String getPasswrod() {
    return mPasswrod;
  }

  public void setPasswrod(String passwrod) {
    mPasswrod = passwrod;
  }

  public int getSignalLevel() {
    return mSignalLevel;
  }

  public interface Fields extends BaseModel.Fields {

    final String SSID = "ssid";

    final String BSSID = "bssid";

    final String CAPABILITIES = "capabilities";

    final String FREQUENCY = "frequency";

    final String NAME = "name";

    final String ADDRESS = "address";

    final String PASSWORD = "password";
  }

}
