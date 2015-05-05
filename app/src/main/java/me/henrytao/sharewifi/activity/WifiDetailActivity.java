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

import com.orhanobut.logger.Logger;

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
import me.henrytao.sharewifi.config.Constants;
import me.henrytao.sharewifi.fragment.WifiDetailFragment;
import me.henrytao.sharewifi.fragment.WifiDetailFragment.WifiDetailInterface;
import me.henrytao.sharewifi.model.WifiModel;
import me.henrytao.sharewifi.model.orm.DeserializerException;
import me.henrytao.sharewifi.model.orm.SerializerException;
import me.henrytao.sharewifi.util.JsonUtils;

public class WifiDetailActivity extends MdToolbarActivity implements WifiDetailInterface {

  public static Intent getIntent(Context context, String wifiID) {
    Intent intent = new Intent(context, WifiDetailActivity.class);
    intent.putExtra(Constants.EXTRA.ID, wifiID);
    return intent;
  }

  public static Intent getIntent(Context context, WifiModel wifi) {
    Intent intent = new Intent(context, WifiDetailActivity.class);
    try {
      intent.putExtra(Constants.EXTRA.MODEL, JsonUtils.encode(wifi.serialize()));
    } catch (SerializerException e) {
      Logger.w(e.getMessage());
    }
    return intent;
  }

  private WifiDetailFragment mWifiDetailFragment;

  @InjectView(R.id.ssid)
  TextView vSSID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wifi_detail);
    ButterKnife.inject(this);

    mWifiDetailFragment = WifiDetailFragment
        .newInstance(getIntent().getStringExtra(Constants.EXTRA.ID), getIntent().getStringExtra(Constants.EXTRA.MODEL));
    mWifiDetailFragment = WifiDetailFragment.newInstance(getIntent().getExtras());
    getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.fragment, mWifiDetailFragment)
        .commit();
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
  public void onSSIDChanged(String SSID) {
    vSSID.setText(TextUtils.isEmpty(SSID) ? "" : SSID);
  }
}
