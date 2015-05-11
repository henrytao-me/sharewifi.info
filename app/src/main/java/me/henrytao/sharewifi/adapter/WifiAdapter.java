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
import lombok.Getter;
import lombok.experimental.Accessors;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.helper.ResourceHelper;
import me.henrytao.sharewifi.model.WifiModel;
import me.henrytao.sharewifi.service.WifiService;

/**
 * Created by henrytao on 4/27/15.
 */
@Accessors(prefix = "m")
public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.ViewHolder> {

  @Getter private Context mContext;

  private List<WifiModel> mListWifi;

  private OnClickListener mOnClickListener;

  public WifiAdapter(Context context, List<WifiModel> listWifi) {
    mContext = context;
    mListWifi = listWifi;
  }

  @Override
  public int getItemCount() {
    return mListWifi.size();
  }

  @Override
  public void onBindViewHolder(WifiAdapter.ViewHolder holder, int position) {
    holder.bind(mListWifi.get(position), WifiService.isCurrentWifi(mContext, mListWifi.get(position)));
  }

  @Override
  public WifiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi, parent, false);
    ViewHolder viewHolder = new ViewHolder(getContext(), view);
    view.setOnClickListener(v -> {
      if (mOnClickListener != null) {
        mOnClickListener.onWifiAdapterClick(view, viewHolder.getData());
      }
    });
    viewHolder.vButtonInfo.setOnClickListener(v -> {
      if (mOnClickListener != null) {
        mOnClickListener.onWifiAdapterInfoClick(view, viewHolder.getData());
      }
    });
    return viewHolder;
  }

  public void setOnClickListener(OnClickListener onClickListener) {
    mOnClickListener = onClickListener;
  }

  public interface OnClickListener {

    void onWifiAdapterClick(View view, WifiModel data);

    void onWifiAdapterInfoClick(View view, WifiModel data);
  }

  @Accessors(prefix = "m")
  public static class ViewHolder extends RecyclerView.ViewHolder {

    @Getter Context mContext;

    @Getter WifiModel mData;

    @InjectView(R.id.address)
    TextView vAddress;

    @InjectView(R.id.button_info)
    View vButtonInfo;

    @InjectView(R.id.connected_status)
    TextView vConnectedStatus;

    @InjectView(R.id.name)
    TextView vName;

    @InjectView(R.id.ssid)
    TextView vSSID;

    @InjectView(R.id.signal_level)
    ImageView vSignalLevel;

    public ViewHolder(Context context, View view) {
      super(view);
      mContext = context;
      ButterKnife.inject(this, view);
    }

    public void bind(WifiModel data, boolean isConnected) {
      mData = data;
      vSSID.setText(data.getSSID());
      vName.setText(data.getName());
      vAddress.setText(data.getAddress());
      vConnectedStatus.setText(ResourceHelper.getWifiConnectedStatus(getContext(), isConnected));
      vSignalLevel.setImageResource(ResourceHelper.getDrawableSignalLevelResource(
          data.getSignalLevel(),
          data.isPasswordRequired(),
          data.hasPassword()));
    }
  }
}
