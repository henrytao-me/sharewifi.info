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
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 3/28/15.
 */
public class MainActivity extends MdDrawerLayoutActivity {

  @InjectView(R.id.container)
  DrawerLayout mDrawerLayout;

  @InjectView(R.id.content)
  View mContentView;

  @InjectView(R.id.drawer)
  View mDrawerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    switch (item.getItemId()) {

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

}
