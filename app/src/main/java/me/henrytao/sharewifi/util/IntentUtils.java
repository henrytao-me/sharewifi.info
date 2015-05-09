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

package me.henrytao.sharewifi.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;
import me.henrytao.sharewifi.model.orm.BaseModel;

/**
 * Created by henrytao on 4/15/15.
 */
public class IntentUtils {


  public static boolean isAvailable(Context context, Intent intent) {
    PackageManager mgr = context.getPackageManager();
    List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
    return list.size() > 0;
  }

  public static class Bundle<T> implements Serializable {

    String mId;

    T mModel;

    public Bundle(String id) {
      this(id, null);
    }

    public Bundle(T model) {
      this(null, model);
    }

    public Bundle(@Nullable String id, @Nullable T model) {
      mId = id;
      mModel = model;
    }

    public String getId() {
      return mId;
    }

    public T getModel() {
      return mModel == null ? null : (T) mModel;
    }
  }

}
