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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.InjectView;
import me.henrytao.sharewifi.R;
import me.henrytao.sharewifi.util.ViewUtils;

/**
 * Created by henrytao on 5/10/15.
 */
public class DialogConnectToNewWifiHolder extends BaseHolder<DialogConnectToNewWifiHolder> {

  @InjectView(R.id.password)
  EditText vPassword;

  @InjectView(R.id.show_password)
  CheckBox vShowPassword;

  private OnPasswordChangeListener mOnPasswordChangeListener;

  public DialogConnectToNewWifiHolder(Context context) {
    super(context);
    vPassword.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
        if (mOnPasswordChangeListener != null) {
          mOnPasswordChangeListener.onPasswordChanged(s.toString());
        }
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }
    });
    vShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> setPasswordTextType(isChecked));
  }

  @Override
  protected int getLayoutId() {
    return R.layout.dialog_connect_to_new_wifi;
  }

  @Override
  protected DialogConnectToNewWifiHolder onPause() {
    super.onPause();
    ViewUtils.closeKeyBoard(getContext(), vPassword);
    return this;
  }

  @Override
  protected DialogConnectToNewWifiHolder onResume() {
    super.onResume();
    ViewUtils.showKeyboard(getContext(), vPassword);
    return this;
  }

  public String getPassword() {
    return vPassword.getText().toString();
  }

  public DialogConnectToNewWifiHolder setOnPasswordChangeListener(OnPasswordChangeListener onPasswordChangeListener) {
    mOnPasswordChangeListener = onPasswordChangeListener;
    return this;
  }

  private void setPasswordTextType(boolean showPassword) {
    vPassword.setInputType(showPassword ? EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        : EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
    ViewUtils.moveCursorToTheEnd(vPassword);
  }

  public interface OnPasswordChangeListener {

    void onPasswordChanged(CharSequence s);
  }
}
