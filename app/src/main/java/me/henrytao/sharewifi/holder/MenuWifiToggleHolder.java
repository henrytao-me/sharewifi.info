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

package me.henrytao.sharewifi.holder;

import android.content.Context;

import butterknife.InjectView;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.service.WifiService;
import me.henrytao.sharewifi.widget.CheckableSwitchCompat;

/**
 * Created by henrytao on 5/10/15.
 */
public class MenuWifiToggleHolder extends BaseHolder<MenuWifiToggleHolder> {

  @InjectView(R.id.wifi_toggle)
  CheckableSwitchCompat vWifiToggle;

  public MenuWifiToggleHolder(Context context) {
    super(context);
    vWifiToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
      WifiService.setWifiEnabled(getContext(), isChecked);
    });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.menu_wifi_toggle;
  }

  @Override
  protected MenuWifiToggleHolder onResume() {
    super.onResume();
    addSubscription(WifiService.observeWifiState(getContext()).subscribe(state -> {
      if (state == WifiService.WIFI_STATE.ENABLED) {
        vWifiToggle.setChecked(true, false);
      } else {
        vWifiToggle.setChecked(false, false);
      }
    }));
    vWifiToggle.setChecked(WifiService.isWifiEnabled(getContext()));
    return this;
  }
}
