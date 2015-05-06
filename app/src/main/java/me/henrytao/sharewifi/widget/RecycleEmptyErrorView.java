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

package me.henrytao.sharewifi.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by henrytao on 5/1/15.
 */
public class RecycleEmptyErrorView extends RecyclerView {

  private boolean isError;

  private View mEmptyView;

  private View mErrorView;

  private int mVisibility;

  final private AdapterDataObserver mObserver = new AdapterDataObserver() {
    @Override
    public void onChanged() {
      updateEmptyView();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      updateEmptyView();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      updateEmptyView();
    }
  };

  public RecycleEmptyErrorView(Context context) {
    super(context);
    mVisibility = getVisibility();
  }

  public RecycleEmptyErrorView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mVisibility = getVisibility();
  }

  public RecycleEmptyErrorView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mVisibility = getVisibility();
  }

  @Override
  public void setAdapter(Adapter adapter) {
    Adapter oldAdapter = getAdapter();
    if (oldAdapter != null) {
      oldAdapter.unregisterAdapterDataObserver(mObserver);
    }
    super.setAdapter(adapter);
    if (adapter != null) {
      adapter.registerAdapterDataObserver(mObserver);
    }
    updateEmptyView();
  }

  @Override
  public void setVisibility(int visibility) {
    super.setVisibility(visibility);
    mVisibility = visibility;
    updateErrorView();
    updateEmptyView();
  }

  public RecycleEmptyErrorView hideErrorView() {
    isError = false;
    updateErrorView();
    updateEmptyView();
    return this;
  }

  public RecycleEmptyErrorView setEmptyView(View emptyView) {
    if (mEmptyView != null) {
      mEmptyView.setVisibility(GONE);
    }
    mEmptyView = emptyView;
    mEmptyView.setVisibility(GONE);
    updateEmptyView();
    return this;
  }

  public RecycleEmptyErrorView setErrorView(View errorView) {
    if (mErrorView != null) {
      mErrorView.setVisibility(GONE);
    }
    mErrorView = errorView;
    mErrorView.setVisibility(GONE);
    updateErrorView();
    updateEmptyView();
    return this;
  }

  public RecycleEmptyErrorView showErrorView() {
    isError = true;
    updateErrorView();
    updateEmptyView();
    return this;
  }

  private boolean shouldShowErrorView() {
    if (mErrorView != null && isError) {
      return true;
    }
    return false;
  }

  private void updateEmptyView() {
    if (mEmptyView != null && getAdapter() != null) {
      boolean isShowEmptyView = getAdapter().getItemCount() == 0;
      mEmptyView.setVisibility(isShowEmptyView && !shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
      super.setVisibility(!isShowEmptyView && !shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
    }
  }

  private void updateErrorView() {
    if (mErrorView != null) {
      mErrorView.setVisibility(shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
    }
  }

}
