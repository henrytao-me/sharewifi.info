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

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 4/17/15.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

  private Context mContext;

  private ArrayList<String> mList;

  public LocationAdapter(Context context, ArrayList<String> list) {
    mContext = context;
    mList = list;
  }

  @Override
  public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(LocationAdapter.ViewHolder holder, int position) {
    holder.mTitle.setText(mList.get(position));
    holder.mDesc.setText("No description yet");
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.title)
    TextView mTitle;

    @InjectView(R.id.desc)
    TextView mDesc;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.inject(this, view);
    }
  }
}
