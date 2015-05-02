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

package me.henrytao.sharewifi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.model.entity.WifiModel;
import me.henrytao.sharewifi.service.WifiService;

/**
 * Created by henrytao on 4/27/15.
 */
public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.ViewHolder> {

  private Context mContext;

  private List<WifiModel> mWifiList;

  private OnClickListener mOnClickListener;

  public WifiAdapter(Context context, List<WifiModel> wifiList) {
    mContext = context;
    mWifiList = wifiList;
  }

  @Override
  public WifiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi, parent, false);
    ViewHolder viewHolder = new ViewHolder(view);
    view.setOnClickListener(v -> {
      if (mOnClickListener != null) {
        mOnClickListener.onWifiAdapterClick(view, viewHolder.mData);
      }
    });
    viewHolder.mButtonInfo.setOnClickListener(v -> {
      if (mOnClickListener != null) {
        mOnClickListener.onWifiAdapterInfoClick(view, viewHolder.mData);
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(WifiAdapter.ViewHolder holder, int position) {
    holder.bind(mWifiList.get(position), WifiService.isCurrentWifi(mContext, mWifiList.get(position)));
  }

  @Override
  public int getItemCount() {
    return mWifiList.size();
  }

  public void setOnClickListener(OnClickListener onClickListener) {
    mOnClickListener = onClickListener;
  }

  public interface OnClickListener {

    void onWifiAdapterClick(View view, WifiModel data);

    void onWifiAdapterInfoClick(View view, WifiModel data);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    WifiModel mData;

    int[] mSignalLevelRes = {
        R.drawable.ic_signal_wifi_0_bar_lock,
        R.drawable.ic_signal_wifi_1_bar_lock,
        R.drawable.ic_signal_wifi_2_bar_lock,
        R.drawable.ic_signal_wifi_3_bar_lock,
        R.drawable.ic_signal_wifi_4_bar_lock
    };

    @InjectView(R.id.signal_level)
    ImageView mViewSignalLevel;

    @InjectView(R.id.ssid)
    TextView mViewSSID;

    @InjectView(R.id.connected_state)
    TextView mViewConnectedState;

    @InjectView(R.id.name)
    TextView mViewName;

    @InjectView(R.id.address)
    TextView mViewAddress;

    @InjectView(R.id.button_info)
    View mButtonInfo;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.inject(this, view);
    }

    public void bind(WifiModel data, boolean isConnected) {
      mData = data;
      mViewSSID.setText(data.getSSID());
      mViewName.setText(data.getName());
      mViewAddress.setText(data.getAddress());
      renderSignalLevel(data.getSignalLevel());
      renderConnectedState(isConnected);
    }

    private void renderSignalLevel(int signalLevel) {
      if (signalLevel < 0) {
        signalLevel = 0;
      } else if (signalLevel > mSignalLevelRes.length - 1) {
        signalLevel = mSignalLevelRes.length - 1;
      }
      mViewSignalLevel.setImageResource(mSignalLevelRes[signalLevel]);
    }

    private void renderConnectedState(boolean isConnected) {
      if (isConnected) {
        mViewConnectedState.setText(R.string.item_wifi_is_connected);
      } else {
        mViewConnectedState.setText(null);
      }
    }
  }
}
