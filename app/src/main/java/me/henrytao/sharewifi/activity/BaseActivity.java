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
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import rx.Subscription;

/**
 * Created by henrytao on 4/14/15.
 */
public class BaseActivity extends AppCompatActivity {

  protected Map<String, Subscription> mSubscription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    onInitializeIntentExtra(getIntent());
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mSubscription != null) {
      for (Subscription subscription : mSubscription.values()) {
        subscription.unsubscribe();
      }
      mSubscription.clear();
    }
  }

  protected void addSubscription(String key, Subscription subscription) {
    if (mSubscription == null) {
      mSubscription = new HashMap<>();
    }
    if (TextUtils.isEmpty(key)) {
      key = UUID.randomUUID().toString();
    }
    mSubscription.put(key, subscription);
  }

  protected void addSubscription(Subscription subscription) {
    addSubscription(null, subscription);
  }

  protected void onInitializeIntentExtra(Intent intent) {

  }

  protected void removeSubscription(String key) {
    if (mSubscription.get(key) != null) {
      mSubscription.get(key).unsubscribe();
      mSubscription.remove(key);
    }
  }

}
