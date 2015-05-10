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

import me.henrytao.sharewifi.widget.AlertDialogBuilder;

/**
 * Created by henrytao on 5/7/15.
 */
public class ViewHelper {

  public static AlertDialogBuilder getConnectToNewWifiDialog(Context context) {
    AlertDialogBuilder builder = new AlertDialogBuilder(context)
        .setTitle("hello moto")
        .setMessage("this is a message")
        .setPositiveButton("Connect")
        .setNegativeButton("Cancel");
    return builder;
  }

}
