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

package me.henrytao.sharewifi.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.model.WifiModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class WifiDetailFragment extends Fragment {

  WifiModel mWifi;

  public WifiDetailFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_wifi_detail, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getActivity() instanceof WifiDetailInterface) {
      WifiDetailInterface wifiDetailInterface = (WifiDetailInterface) getActivity();
      mWifi = wifiDetailInterface.getWifi();
      if (mWifi != null) {
        wifiDetailInterface.setSSID(mWifi.getSSID());
      }
    }
  }

  public interface WifiDetailInterface {

    WifiModel getWifi();

    void setSSID(String SSID);
  }
}
