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

package me.henrytao.sharewifi.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import me.henrytao.sharewifi.activity.BaseActivity;
import me.henrytao.sharewifi.util.ViewUtils;
import rx.Subscription;

/**
 * Created by henrytao on 5/10/15.
 */
public abstract class BaseHolder<T extends BaseHolder> {

  protected abstract int getLayoutId();

  private Context mContext;

  private List<String> mSubscriptionKeys;

  private View mView;

  public BaseHolder(Context context) {
    mContext = context;
    ButterKnife.inject(this, getView());
  }

  public T addSubscription(String key, Subscription subscription) {
    if (getContext() instanceof BaseActivity) {
      if (mSubscriptionKeys == null) {
        mSubscriptionKeys = new ArrayList<>();
      }
      if (TextUtils.isEmpty(key)) {
        key = UUID.randomUUID().toString();
      }
      mSubscriptionKeys.add(key);
      BaseActivity activity = (BaseActivity) getContext();
      activity.addSubscription(key, subscription);
    }
    return (T) this;
  }

  public T addSubscription(Subscription subscription) {
    addSubscription(null, subscription);
    return (T) this;
  }

  public T destroy() {
    onDestroy();
    return (T) this;
  }

  public Context getContext() {
    return mContext;
  }

  public View getView() {
    if (mView == null) {
      mView = ViewUtils.inflate(getContext(), getLayoutId());
    }
    return mView;
  }

  public T pause() {
    onPause();
    return (T) this;
  }

  public T removeSubscription(String key) {
    if (getContext() instanceof BaseActivity) {
      BaseActivity activity = (BaseActivity) getContext();
      activity.removeSubscription(key);
    }
    return (T) this;
  }

  public T resume() {
    onResume();
    return (T) this;
  }

  protected T onDestroy() {
    ButterKnife.reset(this);
    return (T) this;
  }

  protected T onPause() {
    if (mSubscriptionKeys != null) {
      for (String key : mSubscriptionKeys) {
        removeSubscription(key);
      }
    }
    return (T) this;
  }

  protected T onResume() {
    return (T) this;
  }

}
