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

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.hamcrest.CoreMatchers;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import me.henrytao.sharewifi.RobolectricGradleTestRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by henrytao on 5/3/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 21)
public class JsonUtilsTest {

  @Test
  public void decode_single() throws JSONException {
    Map<String, Object> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", "2");
    map.put("key3", "false");

    Map<String, Object> res = JsonUtils.decode(map.toString());
    assertThat(res.size(), equalTo(map.size()));
    assertThat(res.get("key1").toString(), equalTo(map.get("key1").toString()));
    assertThat(res.get("key2").toString(), equalTo(map.get("key2").toString()));
    assertThat(res.get("key3").toString(), equalTo(map.get("key3").toString()));
  }

  @Test
  public void decode_nested() throws JSONException {
    Map<String, Object> nested = new HashMap<>();
    nested.put("key1", "value1");
    nested.put("key2", 2);
    nested.put("key3", false);

    Map<String, Object> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", 2);
    map.put("key3", false);
    map.put("key4", nested);

    Map<String, Object> target = new HashMap<>();
    target.put("key1", "value1");
    target.put("key2", 2);
    target.put("key3", false);
    target.put("key4", JsonUtils.encode(nested));

    Map<String, Object> res = JsonUtils.decode(JsonUtils.encode(map));
    assertThat(res.size(), equalTo(target.size()));
    assertThat(res.get("key1").toString(), equalTo(target.get("key1").toString()));
    assertThat(res.get("key2").toString(), equalTo(target.get("key2").toString()));
    assertThat(res.get("key3").toString(), equalTo(target.get("key3").toString()));
    assertThat(res.get("key4").toString(), equalTo(target.get("key4").toString()));
  }

  @Test
  public void decode_success() {
    assertThat(JsonUtils.decode("{hello: 1, moto: 2}"), notNullValue());
    assertThat(JsonUtils.decode("{\"hello\": 1, \"moto\": 2}"), notNullValue());
  }

  @Test
  public void decode_failed() {
    assertThat(JsonUtils.decode(""), nullValue());
    assertThat(JsonUtils.decode("true"), nullValue());
    assertThat(JsonUtils.decode("0"), nullValue());
    assertThat(JsonUtils.decode("{hello: 1, moto: 2,}"), nullValue());
    assertThat(JsonUtils.decode("{\"hello\": 1, \"moto\": 2,}"), nullValue());
  }

}
