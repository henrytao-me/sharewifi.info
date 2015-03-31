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

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by henrytao on 3/31/15.
 */
public class DbHelper extends SQLiteOpenHelper {

  public static final int VERSION = 1;

  public static final String DATABASE_NAME = "data";

  public static final String TABLE_NAME = "comments_table";

  public static final String C_ID = "_id";

  public static final String NAME = "name";

  public static final String COMMENT = "comment";

  public static final String EMAIL = "email";

  public static final String TIME = "time";

  private final String createDb = "create table if not exists " + TABLE_NAME + " ( "
      + C_ID + " text, "
      + NAME + " text, "
      + COMMENT + " text, "
      + EMAIL + " text, "
      + TIME + " text); ";

  public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, DATABASE_NAME, factory, VERSION);
  }

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(createDb);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table " + TABLE_NAME);
  }
}
