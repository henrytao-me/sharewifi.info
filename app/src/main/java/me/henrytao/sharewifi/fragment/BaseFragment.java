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

package me.henrytao.sharewifi.fragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import rx.Subscription;

/**
 * Created by henrytao on 4/14/15.
 */
public class BaseFragment extends Fragment {

  protected Map<String, Subscription> mSubscription;

  @Override
  public void onPause() {
    super.onPause();
    if (isAdded() && mSubscription != null) {
      for (Subscription subscription : mSubscription.values()) {
        subscription.unsubscribe();
      }
      mSubscription.clear();
    }
  }

  public void addSubscription(String key, Subscription subscription) {
    if (mSubscription == null) {
      mSubscription = new HashMap<>();
    }
    if (TextUtils.isEmpty(key)) {
      key = UUID.randomUUID().toString();
    }
    mSubscription.put(key, subscription);
  }

  public void addSubscription(Subscription subscription) {
    addSubscription(null, subscription);
  }

  public void removeSubscription(String key) {
    if (mSubscription != null && mSubscription.get(key) != null) {
      mSubscription.get(key).unsubscribe();
      mSubscription.remove(key);
    }
  }

}
