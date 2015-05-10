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

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.activity.WifiDetailActivity;
import me.henrytao.sharewifi.adapter.WifiAdapter;
import me.henrytao.sharewifi.config.Constants;
import me.henrytao.sharewifi.model.WifiModel;
import me.henrytao.sharewifi.service.WifiService;
import me.henrytao.sharewifi.util.IntentUtils;
import me.henrytao.sharewifi.util.ResourceUtils;
import me.henrytao.sharewifi.widget.RecycleEmptyErrorView;

/**
 * Created by henrytao on 4/12/15.
 */
public class MainFragment extends BaseFragment implements WifiAdapter.OnClickListener {

  @InjectView(R.id.empty_view)
  View vEmptyView;

  @InjectView(R.id.error_view)
  View vErrorView;

  @InjectView(R.id.list)
  RecycleEmptyErrorView vRecyclerView;

  @InjectView(R.id.swipe_refresh_layout)
  SwipeRefreshLayout vSwipeRefreshLayout;

  @InjectView(R.id.turn_on_wifi_view)
  View vTurnOnWifiView;

  private List<WifiModel> mListWifi = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_refresh:
        if (!vSwipeRefreshLayout.isRefreshing()) {
          refreshContent();
        }
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onResume() {
    super.onResume();
    if (isAdded()) {
      addSubscription(WifiService.observeAvailableWifiList(getActivity())
          .subscribe(list -> {
            mListWifi.clear();
            mListWifi.addAll(list);
            vRecyclerView.getAdapter().notifyDataSetChanged();
          }));
      addSubscription(WifiService.observeWifiState(getActivity()).subscribe(state -> {
        switch (state) {
          case ENABLED:
            vRecyclerView.setErrorView(vErrorView).hideErrorView();
            break;
          case DISABLED:
            vRecyclerView.setErrorView(vTurnOnWifiView).showErrorView();
            break;
        }
      }));
      if (!WifiService.isWifiEnabled(getActivity())) {
        vRecyclerView.setErrorView(vTurnOnWifiView).showErrorView();
      }
    }
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setHasOptionsMenu(true);

    WifiAdapter adapter = new WifiAdapter(getActivity(), mListWifi);
    adapter.setOnClickListener(this);
    vRecyclerView.setHasFixedSize(true);
    vRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    vRecyclerView.setAdapter(adapter);
    vRecyclerView.setEmptyView(vEmptyView);
    vRecyclerView.setErrorView(vTurnOnWifiView).setErrorView(vErrorView);

    vSwipeRefreshLayout.setColorSchemeColors(ResourceUtils.getColorFromAttribute(getActivity(), R.attr.mdColor_primaryPalette));
    vSwipeRefreshLayout.setOnRefreshListener(() -> refreshContent());
  }

  @Override
  public void onWifiAdapterClick(View view, WifiModel data) {

  }

  @Override
  public void onWifiAdapterInfoClick(View view, WifiModel data) {
    startActivity(WifiDetailActivity.getIntent(getActivity(), new IntentUtils.Bundle(data)));
  }

  @OnClick(R.id.turn_on_wifi_view)
  protected void turnOnWifiViewClicked() {
    WifiService.setWifiEnabled(getActivity(), true);
  }

  private void refreshContent() {
    vSwipeRefreshLayout.setRefreshing(true);
    WifiService.startScan(getActivity());
    new Handler().postDelayed(() -> {
      vSwipeRefreshLayout.setRefreshing(false);
    }, Constants.WIFI_REFRESH_TIMEOUT);
  }
}
