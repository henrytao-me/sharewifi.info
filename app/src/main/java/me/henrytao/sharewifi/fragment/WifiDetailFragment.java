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

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.config.Constants;
import me.henrytao.sharewifi.model.WifiModel;
import me.henrytao.sharewifi.service.WifiService;

/**
 * A placeholder fragment containing a simple view.
 */
public class WifiDetailFragment extends Fragment {

  public static WifiDetailFragment newInstance(Bundle bundle) {
    WifiDetailFragment fragment = new WifiDetailFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  public static WifiDetailFragment newInstance(@Nullable String wifiID, @Nullable WifiModel wifiModel) {
    Bundle bundle = new Bundle();
    bundle.putString(Constants.EXTRA.ID, wifiID);
    bundle.putSerializable(Constants.EXTRA.MODEL, wifiModel);
    return newInstance(bundle);
  }

  public static WifiDetailFragment newInstance(String wifiID) {
    return newInstance(wifiID, null);
  }

  public static WifiDetailFragment newInstance(WifiModel wifiModel) {
    return newInstance(null, wifiModel);
  }

  @InjectView(R.id.button_connect)
  Button vButtonConnect;

  private String mWifiID;

  private WifiModel mWifiModel;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    mWifiID = getArguments().getString(Constants.EXTRA.ID);
    Serializable serializable = getArguments().getSerializable(Constants.EXTRA.MODEL);
    mWifiModel = serializable == null ? null : (WifiModel) serializable;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_wifi_detail, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getActivity() instanceof WifiDetailInterface) {
      WifiDetailInterface wifiDetailInterface = (WifiDetailInterface) getActivity();
      if (mWifiModel != null) {
        wifiDetailInterface.onSSIDChanged(mWifiModel.getAddress());
      }
    }
  }

  @OnClick(R.id.button_connect)
  protected void onButtonConnectClicked() {
    WifiService.connectToWifi(getActivity(), mWifiModel.getSSID(), null);
  }

  public interface WifiDetailInterface {

    void onSSIDChanged(String SSID);
  }

}
