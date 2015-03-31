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
import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import me.henrytao.sharewifi.CommentsProvider;
import me.henrytao.sharewifi.DbHelper;
import me.henrytao.sharewifi.R;

/**
 * Created by henrytao on 3/31/15.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  View view;

  SQLiteDatabase db;

  DbHelper dbHelper;

  Button mSend;

  Button mQuery;

  EditText mName;

  EditText mComment;

  EditText mEmail;

  ListView mList;

  String[] columns = {DbHelper.NAME, DbHelper.COMMENT, DbHelper.EMAIL};

  String[] allColumns = {DbHelper.C_ID, DbHelper.NAME, DbHelper.COMMENT, DbHelper.EMAIL};

  private static final int LOADER = 0x01;

  SimpleCursorAdapter adapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    dbHelper = new DbHelper(getActivity());
    db = dbHelper.getWritableDatabase();

    view = inflater.inflate(R.layout.fragment_main, container, false);
    mSend = (Button) view.findViewById(R.id.send);
    mQuery = (Button) view.findViewById(R.id.query);
    mName = (EditText) view.findViewById(R.id.name);
    mComment = (EditText) view.findViewById(R.id.comment);
    mEmail = (EditText) view.findViewById(R.id.email);
    mList = (ListView) view.findViewById(R.id.list);

    int[] to = {R.id.name, R.id.comment, R.id.email};
    adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_row, null, columns, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    mList.setAdapter(adapter);
    getLoaderManager().restartLoader(LOADER, null, this);

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

        getActivity().getContentResolver().insert(CommentsProvider.COMMENTS_URI, data);
      }
    });

    mQuery.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Cursor cursor = db.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
          String name = cursor.getString(cursor.getColumnIndex(DbHelper.NAME));
          String comment = cursor.getString(cursor.getColumnIndex(DbHelper.COMMENT));
          String email = cursor.getString(cursor.getColumnIndex(DbHelper.EMAIL));
          Toast.makeText(getActivity(), "Name = " + name
              + "; Comment = " + comment
              + "; Email = " + email + ";", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
      }
    });

    return view;
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    CursorLoader cursorLoader = new CursorLoader(getActivity(), CommentsProvider.COMMENTS_URI, allColumns, null, null, null);
    return cursorLoader;
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    adapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    adapter.swapCursor(null);
  }

}
