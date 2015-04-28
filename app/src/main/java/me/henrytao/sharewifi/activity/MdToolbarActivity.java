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
import android.view.View;

import butterknife.InjectView;
import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 4/28/15.
 */
public class MdToolbarActivity extends BaseActivity {

  @InjectView(R.id.md_toolbar)
  Toolbar mToolbar;

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    if (mToolbar != null) {
      setSupportActionBar(mToolbar);
      mToolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow_back_white);
      mToolbar.setNavigationOnClickListener((v) -> onNavigationClicked(v));
    }
  }

  protected void onNavigationClicked(View view) {
    onBackPressed();
  }
}
