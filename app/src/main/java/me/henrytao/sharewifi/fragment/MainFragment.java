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

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import me.henrytao.sharewifi.DbHelper;
import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 3/31/15.
 */
public class MainFragment extends Fragment {

  View view;

  SQLiteDatabase db;

  DbHelper dbHelper;

  Button mSend;

  EditText mName;

  EditText mComment;

  EditText mEmail;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    dbHelper = new DbHelper(getActivity());
    db = dbHelper.getWritableDatabase();

    view = inflater.inflate(R.layout.fragment_main, container, false);
    mSend = (Button) view.findViewById(R.id.send);
    mName = (EditText) view.findViewById(R.id.name);
    mComment = (EditText) view.findViewById(R.id.comment);
    mEmail = (EditText) view.findViewById(R.id.email);

    mSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String name, comment, email;
        name = mName.getText().toString();
        comment = mComment.getText().toString();
        email = mEmail.getText().toString();

        ContentValues data = new ContentValues();
        data.put(DbHelper.NAME, name);
        data.put(DbHelper.COMMENT, comment);
        data.put(DbHelper.EMAIL, email);

        db.insert(DbHelper.TABLE_NAME, null, data);
      }
    });

    return view;
  }

}
