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

package me.henrytao.sharewifi.helper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import lombok.Getter;
import lombok.experimental.Accessors;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.holder.BaseHolder;
import me.henrytao.sharewifi.holder.DialogConnectToNewWifiHolder;
import me.henrytao.sharewifi.util.ViewUtils;
import me.henrytao.sharewifi.widget.AlertDialogBuilder;

/**
 * Created by henrytao on 5/7/15.
 */
public class ViewHelper {

  public static BuilderHolder<DialogConnectToNewWifiHolder> getConnectToNewWifiDialog(Context context, String wifiName) {
    View view = ViewUtils.inflate(context, R.layout.dialog_connect_to_new_wifi);
    DialogConnectToNewWifiHolder holder = new DialogConnectToNewWifiHolder(context, view);
    AlertDialogBuilder builder = new AlertDialogBuilder(context)
        .setAutoDismiss(false)
        .setTitle(wifiName)
        .setView(view)
        .setOnShowListener(dialog -> holder.onShowed())
        .setOnDismissListener(dialog -> holder.onClosed())
        .setPositiveButton(R.string.text_connect)
        .setNegativeButton(R.string.text_cancel, (dialog, which) -> dialog.dismiss());
    return new BuilderHolder(builder, holder);
  }

  @Accessors(prefix = "m")
  public static class BuilderHolder<T extends BaseHolder> {

    @Getter private AlertDialogBuilder mBuilder;

    @Getter private T mHolder;

    public BuilderHolder(AlertDialogBuilder builder, @Nullable T holder) {
      mBuilder = builder;
      mHolder = holder;
    }
  }

}
