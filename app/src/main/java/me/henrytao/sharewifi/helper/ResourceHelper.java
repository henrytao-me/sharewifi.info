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

package me.henrytao.sharewifi.helper;

import android.content.Context;
import android.net.NetworkInfo.State;

import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 4/29/15.
 */
public class ResourceHelper {

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

  public static int getDrawableSignalLevelResource(int signalLevel, boolean isPasswordRequired, boolean hasPassword) {
    int type = isPasswordRequired ? (hasPassword ? 2 : 1) : 0;
    if (signalLevel < 0) {
      signalLevel = 0;
    } else if (signalLevel > R_SIGNAL_LEVEL[type].length - 1) {
      signalLevel = R_SIGNAL_LEVEL[type].length - 1;
    }
    return R_SIGNAL_LEVEL[type][signalLevel];
  }

  public static String getWifiConnectedStatus(Context context, boolean isConnected) {
    return isConnected ? context.getString(R.string.item_wifi_is_connected) : "";
  }

  public static String getWifiState(Context context, State detailedState) {
    String res = context.getString(R.string.text_unknow);
    switch (detailedState) {
      case CONNECTING:
        res = context.getString(R.string.text_connecting);
        break;
      case CONNECTED:
        res = context.getString(R.string.text_connected);
        break;
      case SUSPENDED:
        res = context.getString(R.string.text_suspended);
        break;
      case DISCONNECTING:
        res = context.getString(R.string.text_disconnecting);
        break;
      case DISCONNECTED:
        res = context.getString(R.string.text_disconnected);
        break;
    }
    return res;
  }

}
