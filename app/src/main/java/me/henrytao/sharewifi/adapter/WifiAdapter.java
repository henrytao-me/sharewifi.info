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
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 4/27/15.
 */
public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.ViewHolder> {

  private Context mContext;

  private ArrayList<String> mList;

  private OnItemClickListener mOnItemClickListener;

  public WifiAdapter(Context context, ArrayList<String> list) {
    mContext = context;
    mList = list;
  }

  @Override
  public WifiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi, parent, false);
    ViewHolder viewHolder = new ViewHolder(view);
    view.setOnClickListener((v) -> {
      if (mOnItemClickListener != null) {
        mOnItemClickListener.onLocationAdapterItemClick(view, viewHolder.mPosition);
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(WifiAdapter.ViewHolder holder, int position) {
    holder.setPosition(position);
    holder.mName.setText(mList.get(position));
    holder.mStatus.setText("No shared password");
    holder.mDesc.setText("No description yet");
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener {

    public void onLocationAdapterItemClick(View view, int position);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.name)
    TextView mName;

    @InjectView(R.id.status)
    TextView mStatus;

    @InjectView(R.id.desc)
    TextView mDesc;

    int mPosition;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.inject(this, view);
    }

    private void setPosition(int position) {
      mPosition = position;
    }
  }
}
