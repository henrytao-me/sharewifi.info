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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.util.ToastUtils;

public class WifiDetailActivity extends MdToolbarActivity {

  private static String WIFI_ID = "WIFI_ID";

  public static Intent getIntent(Context context, int wifiId) {
    Intent intent = new Intent(context, WifiDetailActivity.class);
    intent.putExtra(WIFI_ID, wifiId);
    return intent;
  }

  private int mWifiId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wifi_detail);
    ButterKnife.inject(this);

    mWifiId = getIntent().getIntExtra(WIFI_ID, -1);
    ToastUtils.showShortToast(this, Integer.toString(mWifiId));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_wifi_detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_edit:
        break;
      case R.id.action_search:
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
