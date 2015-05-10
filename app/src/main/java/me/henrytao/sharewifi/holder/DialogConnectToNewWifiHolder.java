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

package me.henrytao.sharewifi.holder;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.InjectView;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.util.ViewUtils;

/**
 * Created by henrytao on 5/10/15.
 */
public class DialogConnectToNewWifiHolder extends BaseHolder {

  @InjectView(R.id.password)
  EditText vPassword;

  @InjectView(R.id.show_password)
  CheckBox vShowPassword;

  public DialogConnectToNewWifiHolder(Context context, View view) {
    super(context, view);
    vShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> setPasswordTextType(isChecked));
  }

  public String getPassword() {
    return vPassword.getText().toString();
  }

  public void onShowed() {
    ViewUtils.showKeyboard(getContext(), vPassword);
  }

  private void setPasswordTextType(boolean showPassword) {
    vPassword.setInputType(showPassword ? EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        : EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
    ViewUtils.moveCursorToTheEnd(vPassword);
  }
}
