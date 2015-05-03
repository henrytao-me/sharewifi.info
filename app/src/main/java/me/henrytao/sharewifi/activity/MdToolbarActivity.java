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

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.util.ResourceUtils;

/**
 * Created by henrytao on 4/28/15.
 */
public class MdToolbarActivity extends BaseActivity {

  protected Toolbar vToolbar;

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    initToolbar();
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(view);
    initToolbar();
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    initToolbar();
  }

  private void initToolbar() {
    if (getToolbarId() > 0) {
      vToolbar = (Toolbar) findViewById(getToolbarId());
      setSupportActionBar(vToolbar);
      if (R.attr.appIcon_toolbarArrowBack > 0) {
        vToolbar.setNavigationIcon(ResourceUtils.getDrawableIdFromAttribute(this, R.attr.appIcon_toolbarArrowBack));
      }
      vToolbar.setNavigationOnClickListener((v) -> onNavigationClicked(v));
      if (getToolbarContentLayout() > 0) {
        vToolbar.addView(getLayoutInflater().inflate(getToolbarContentLayout(), vToolbar, false));
      }
    }
  }

  protected int getToolbarId() {
    return R.id.md_toolbar;
  }

  protected int getToolbarContentLayout() {
    return 0;
  }

  protected void onNavigationClicked(View view) {
    onBackPressed();
  }

}
