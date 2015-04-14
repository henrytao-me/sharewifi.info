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

import com.quinny898.library.persistentsearch.SearchBox;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 4/12/15.
 */
public class MainFragment extends BaseFragment implements SearchBox.SearchListener {


  @InjectView(R.id.searchbox)
  SearchBox mSearchBox;

  public MainFragment() {
  }

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
    mSearchBox.setLogoText("sharewifi.info");
    mSearchBox.enableVoiceRecognition(this);
    mSearchBox.setMenuListener(() -> {
      Toast.makeText(getActivity(), "Menu click", Toast.LENGTH_LONG).show();
    });
    mSearchBox.setSearchListener(this);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (isAdded() && requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == getActivity().RESULT_OK) {
      ArrayList<String> matches = data
          .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      mSearchBox.populateEditText(matches);
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onSearchOpened() {

  }

  @Override
  public void onSearchCleared() {

  }

  @Override
  public void onSearchClosed() {

  }

  @Override
  public void onSearchTermChanged() {

  }

  @Override
  public void onSearch(String result) {
    Toast.makeText(getActivity(), "Searched", Toast.LENGTH_LONG).show();
  }
}
