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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 4/17/15.
 */
public class LocationAdapter extends BaseAdapter {

  private Context mContext;

  private ArrayList<String> mList;

  public LocationAdapter(Context context, ArrayList<String> list) {
    mContext = context;
    mList = list;
  }

  @Override
  public int getCount() {
    return mList.size();
  }

  @Override
  public String getItem(int i) {
    return mList.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    if (view == null) {
      view = initView(i, viewGroup);
    }
    renderView(i, view);
    return view;
  }

  protected View initView(int i, ViewGroup viewGroup) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_location, viewGroup, false);
    ViewHolder viewHolder = new ViewHolder(view);
    view.setTag(R.id.view_holder, viewHolder);
    return view;
  }

  protected void renderView(int i, View view) {
    ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.view_holder);
    viewHolder.mTextView1.setText(getItem(i));
  }

  protected static class ViewHolder {

    @InjectView(R.id.imageView1)
    ImageView mImageView;

    @InjectView(R.id.textView1)
    TextView mTextView1;

    @InjectView(R.id.textView2)
    TextView mTextView2;

    public ViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }
}
