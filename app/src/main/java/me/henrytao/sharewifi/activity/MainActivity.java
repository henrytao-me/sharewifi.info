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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.service.WifiService;
import me.henrytao.sharewifi.util.ToastUtils;
import rx.Observable;
import rx.Subscription;

/**
 * Created by henrytao on 3/28/15.
 */
public class MainActivity extends MdDrawerLayoutActivity {

  private Subscription wifiSubscription;

  @InjectView(R.id.container)
  DrawerLayout mDrawerLayout;

  @InjectView(R.id.content)
  View mContentView;

  @InjectView(R.id.drawer)
  View mDrawerView;

  @InjectView(R.id.md_toolbar)
  Toolbar mToolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);

    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.drawable.ic_toolbar_menu_white);
    mToolbar.setNavigationOnClickListener((v) -> openDrawer());

    ToastUtils.showShortToast(this, "onCreate");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        startActivity(SearchActivity.getIntent(this));
        break;
      case R.id.action_refresh:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public DrawerLayout getDrawerLayout() {
    return mDrawerLayout;
  }

  @Override
  public View getContentView() {
    return mContentView;
  }

  @Override
  public View getDrawerView() {
    return mDrawerView;
  }

  @Override
  protected void onPause() {
    super.onPause();
    ToastUtils.showShortToast(this, "onPause");
    wifiSubscription.unsubscribe();
  }

  @Override
  protected void onResume() {
    super.onResume();
    ToastUtils.showShortToast(this, "onResume");
    wifiSubscription = WifiService.getAvailableWifi(this)
        .subscribe(value -> {
          ToastUtils.showShortToast(this, value);
        }, throwable -> {

        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ToastUtils.showShortToast(this, "onDestroy");
  }
}
