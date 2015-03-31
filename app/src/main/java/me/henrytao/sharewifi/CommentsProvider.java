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

package me.henrytao.sharewifi;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by henrytao on 3/31/15.
 */
public class CommentsProvider extends ContentProvider {

  private static final String AUTH = "me.henrytao.sharewifi.CommentsProvider";

  public static final Uri COMMENTS_URI = Uri.parse("content://" + AUTH + "/" + DbHelper.TABLE_NAME);

  final static int COMMENTS = 1;

  SQLiteDatabase db;

  DbHelper dbHelper;

  private static final UriMatcher uriMatcher;

  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(AUTH, DbHelper.TABLE_NAME, COMMENTS);
  }

  @Override
  public boolean onCreate() {
    dbHelper = new DbHelper(getContext());
    return true;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    db = dbHelper.getWritableDatabase();
    if (uriMatcher.match(uri) == COMMENTS) {
      db.insert(dbHelper.TABLE_NAME, null, values);
    }
    db.close();
    getContext().getContentResolver().notifyChange(uri, null);
    return null;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    db = dbHelper.getReadableDatabase();
    Cursor cursor = db.query(DbHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  @Override
  public String getType(Uri uri) {
    return null;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }
}
