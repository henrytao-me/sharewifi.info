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

import android.content.Context;
import android.content.Intent;
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
import me.henrytao.sharewifi.util.IntentUtils;

public class WifiDetailActivity extends MdToolbarActivity implements WifiDetailInterface {

  public static Intent getIntent(Context context, IntentUtils.Bundle<WifiModel> bundle) {
    Intent intent = new Intent(context, WifiDetailActivity.class);
    intent.putExtra(Constants.EXTRA.BUNDLE, bundle);
    return intent;
  }

  @InjectView(R.id.ssid)
  TextView vSSID;

  private WifiDetailFragment mWifiDetailFragment;

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
  public void onSSIDChanged(String SSID) {
    vSSID.setText(TextUtils.isEmpty(SSID) ? "" : SSID);
  }

  @Override
  protected int getToolbarContentResource() {
    return R.layout.view_toolbar_wifi_detail;
  }

  @Override
  protected int getToolbarResource() {
    return R.id.toolbar;
  }

  @Override
  protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wifi_detail);
    ButterKnife.inject(this);

    mWifiDetailFragment = WifiDetailFragment
        .newInstance((IntentUtils.Bundle) getIntent().getSerializableExtra(Constants.EXTRA.BUNDLE));
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment, mWifiDetailFragment)
        .commit();
  }
}
