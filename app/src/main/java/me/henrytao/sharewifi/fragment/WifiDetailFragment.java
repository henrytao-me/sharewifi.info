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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.config.Constants;
import me.henrytao.sharewifi.helper.ResourceHelper;
import me.henrytao.sharewifi.helper.ViewHelper;
import me.henrytao.sharewifi.helper.ViewHelper.BuilderHolder;
import me.henrytao.sharewifi.holder.DialogConnectToNewWifiHolder;
import me.henrytao.sharewifi.model.WifiModel;
import me.henrytao.sharewifi.service.WifiService;
import me.henrytao.sharewifi.util.IntentUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class WifiDetailFragment extends BaseFragment {

  public static WifiDetailFragment newInstance(IntentUtils.Bundle<WifiModel> bundle) {
    WifiDetailFragment fragment = new WifiDetailFragment();
    Bundle args = new Bundle();
    args.putSerializable(Constants.EXTRA.BUNDLE, bundle);
    fragment.setArguments(args);
    return fragment;
  }

  @InjectView(R.id.button_connect)
  Button vButtonConnect;

  @InjectView(R.id.status)
  TextView vStatus;

  private String mWifiID;

  private WifiModel mWifiModel;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    IntentUtils.Bundle<WifiModel> bundle = (IntentUtils.Bundle<WifiModel>) getArguments().getSerializable(Constants.EXTRA.BUNDLE);
    mWifiID = bundle.getId();
    mWifiModel = bundle.getModel();
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
  public void onResume() {
    super.onResume();
    if (isAdded()) {
      addSubscription(WifiService.observeNetworkState(getActivity())
          .subscribe(state -> {
            vStatus.setText(ResourceHelper.getWifiState(getActivity(), state));
          }));
    }
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getActivity() instanceof WifiDetailInterface) {
      WifiDetailInterface wifiDetailInterface = (WifiDetailInterface) getActivity();
      if (mWifiModel != null) {
        wifiDetailInterface.onSSIDChanged(ResourceHelper.getWifiName(getActivity(), mWifiModel.getSSID(), mWifiModel.getFrequency()));
      }
    }
  }

  @OnClick(R.id.button_connect)
  protected void onButtonConnectClicked() {
    BuilderHolder<DialogConnectToNewWifiHolder> builderHolder = ViewHelper.getConnectToNewWifiDialog(
        getActivity(), ResourceHelper.getWifiName(getActivity(), mWifiModel.getSSID(), mWifiModel.getFrequency()));
    builderHolder.getBuilder()
        .setOnPositiveClickListener((dialog, which) -> {
          WifiService.connectToWifi(getActivity(), mWifiModel.getSSID(), builderHolder.getHolder().getPassword());
          dialog.dismiss();
        }).show();
  }

  public interface WifiDetailInterface {

    void onSSIDChanged(String SSID);
  }

}
