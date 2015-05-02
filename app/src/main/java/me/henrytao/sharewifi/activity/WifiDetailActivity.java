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

package me.henrytao.sharewifi.activity;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.fragment.WifiDetailFragment;
import me.henrytao.sharewifi.fragment.WifiDetailFragment.WifiDetailInterface;
import me.henrytao.sharewifi.model.entity.WifiModel;
import me.henrytao.sharewifi.util.ToastUtils;

public class WifiDetailActivity extends MdToolbarActivity implements WifiDetailInterface {

  private static String WIFI_MODEL = "WIFI_MODEL";

  public static Intent getIntent(Context context, WifiModel wifi) {
    Intent intent = new Intent(context, WifiDetailActivity.class);
    try {
      intent.putExtra(WIFI_MODEL, wifi.serialize().toString());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return intent;
  }

  private WifiModel mWifi = new WifiModel();

  @InjectView(R.id.item_ssid)
  TextView mItemSSID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wifi_detail);
    ButterKnife.inject(this);

    try {
      mWifi.deserialize(getIntent().getStringExtra(WIFI_MODEL));
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_wifi_detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {

    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected int getToolbarContentLayout() {
    return R.layout.view_toolbar_wifi_detail;
  }

  @Override
  public WifiModel getWifi() {
    return mWifi;
  }

  @Override
  public void setSSID(String SSID) {
    if (!TextUtils.isEmpty(SSID)) {
      mItemSSID.setText(SSID);
    }
  }
}
