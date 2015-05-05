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

package me.henrytao.sharewifi.model.orm.benchmark;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by henrytao on 5/5/15.
 */
public class ParcelableModel implements Parcelable {

  private String mName;

  private int mAge;

  private long mCreatedAt;

  public ParcelableModel(String name, int age, long createdAt) {
    mName = name;
    mAge = age;
    mCreatedAt = createdAt;
  }

  public ParcelableModel(Parcel in) {
    mName = in.readString();
    mAge = in.readInt();
    mCreatedAt = in.readLong();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mName);
    dest.writeInt(mAge);
    dest.writeLong(mCreatedAt);
  }

  public static final Creator<ParcelableModel> CREATOR = new Creator<ParcelableModel>() {
    @Override
    public ParcelableModel createFromParcel(Parcel source) {
      return new ParcelableModel(source);
    }

    @Override
    public ParcelableModel[] newArray(int size) {
      return new ParcelableModel[size];
    }
  };

  public String getName() {
    return mName;
  }

  public int getAge() {
    return mAge;
  }

  public long getCreatedAt() {
    return mCreatedAt;
  }
}
