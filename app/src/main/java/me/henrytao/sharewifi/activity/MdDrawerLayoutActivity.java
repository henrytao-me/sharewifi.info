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
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 3/28/15.
 */
public abstract class MdDrawerLayoutActivity extends BaseActivity {

  ActionBarDrawerToggle mDrawerToggle;

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mDrawerToggle = new ActionBarDrawerToggle(this, getDrawerLayout(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    if (isValidated()) {
      mDrawerToggle = new ActionBarDrawerToggle(
          this,
          getDrawerLayout(),
          R.string.navigation_drawer_open,
          R.string.navigation_drawer_close) {

        @Override
        public void onDrawerClosed(View drawerView) {
          super.onDrawerClosed(drawerView);
          MdDrawerLayoutActivity.this.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
          super.onDrawerOpened(drawerView);
          MdDrawerLayoutActivity.this.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
          super.onDrawerSlide(drawerView, slideOffset);
          MdDrawerLayoutActivity.this.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
          super.onDrawerStateChanged(newState);
          MdDrawerLayoutActivity.this.onDrawerStateChanged(newState);
        }
      };
      getDrawerLayout().setDrawerListener(mDrawerToggle);
    }
  }

  public abstract View getDrawerContent();

  public abstract DrawerLayout getDrawerLayout();

  public abstract View getDrawerNavigation();

  public void onDrawerClosed(View drawerView) {
    // todo
  }

  public void onDrawerOpened(View drawerView) {
    // todo
  }

  public void onDrawerSlide(View drawerView, float slideOffset) {
    // todo
  }

  public void onDrawerStateChanged(int newState) {
    // todo
  }

  public void openDrawer() {
    if (isValidated()) {
      getDrawerLayout().openDrawer(getDrawerNavigation());
    }
  }

  private boolean isValidated() {
    return getDrawerLayout() != null && getDrawerContent() != null && getDrawerNavigation() != null;
  }
}
