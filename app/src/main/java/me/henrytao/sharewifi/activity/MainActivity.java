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

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.holder.MenuWifiToggleHolder;

/**
 * Created by henrytao on 3/28/15.
 */
public class MainActivity extends BaseDrawerLayoutActivity {

  MenuWifiToggleHolder mMenuWifiToggleHolder;

  @InjectView(R.id.toolbar)
  Toolbar vToolbar;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    mMenuWifiToggleHolder = new MenuWifiToggleHolder(this).resume();
    MenuItem item = menu.findItem(R.id.action_wifi_toggle);
    item.setActionView(mMenuWifiToggleHolder.getView());
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        startActivity(SearchActivity.getIntent(this));
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected int getDrawerContentResource() {
    return R.id.content;
  }

  @Override
  protected int getDrawerLayoutResource() {
    return R.id.container;
  }

  @Override
  protected int getDrawerNavigationResource() {
    return R.id.navigation;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);

    setSupportActionBar(vToolbar);
    vToolbar.setNavigationOnClickListener((v) -> openDrawer());
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mMenuWifiToggleHolder != null) {
      mMenuWifiToggleHolder.pause();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mMenuWifiToggleHolder != null) {
      mMenuWifiToggleHolder.resume();
    }
  }
}
