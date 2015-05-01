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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.activity.WifiDetailActivity;
import me.henrytao.sharewifi.adapter.WifiAdapter;
import me.henrytao.sharewifi.model.entity.WifiModel;
import me.henrytao.sharewifi.service.WifiService;
import rx.Subscription;

/**
 * Created by henrytao on 4/12/15.
 */
public class MainFragment extends BaseFragment implements WifiAdapter.OnClickListener {

  private Subscription mWifiSubscription;

  private List<WifiModel> mList = new ArrayList<>();

  @InjectView(R.id.list)
  RecyclerView mRecyclerView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
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
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    WifiAdapter adapter = new WifiAdapter(getActivity(), mList);
    adapter.setOnClickListener(this);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mRecyclerView.setAdapter(adapter);
  }

  @Override
  public void onPause() {
    super.onPause();
    if (isAdded()) {
      mWifiSubscription.unsubscribe();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (isAdded()) {
      mWifiSubscription = WifiService.getAvailableWifi(getActivity())
          .subscribe(list -> {
            mList.clear();
            mList.addAll(list);
            mRecyclerView.getAdapter().notifyDataSetChanged();
          }, throwable -> {

          });
    }
  }

  @Override
  public void onWifiAdapterClick(View view, WifiModel data) {

  }

  @Override
  public void onWifiAdapterInfoClick(View view, WifiModel data) {
    startActivity(WifiDetailActivity.getIntent(getActivity(), 0));
  }

}
