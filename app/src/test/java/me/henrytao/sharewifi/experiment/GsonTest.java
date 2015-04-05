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

package me.henrytao.sharewifi.experiment;

import com.google.gson.Gson;

import org.apache.maven.artifact.ant.shaded.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.henrytao.sharewifi.model.entity.User;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by henrytao on 3/30/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class GsonTest {

  @Test
  public void serialization() {
    Gson gson = new Gson();
    assertThat(gson.toJson(1), equalTo("1"));
    int[] values = {1};
    assertThat(gson.toJson(values), equalTo("[1]"));
  }

  @Test
  public void deserialization() {
    Gson gson = new Gson();
    assertThat(gson.fromJson("1", int.class), equalTo(1));
    assertThat(gson.fromJson("false", Boolean.class), equalTo(false));
  }

  @Test
  public void object() {
    Cat obj = new Cat();
    Gson gson = new Gson();
    String json = gson.toJson(obj);
    assertThat(json, equalTo("{\"value1\":1,\"value2\":\"abc\"}"));
    assertTrue(gson.fromJson(json, Cat.class).equals(obj));
  }

  @Test
  public void array() {
    Gson gson = new Gson();
    int[] i = {1, 2, 3};
    assertThat(gson.toJson(i), equalTo("[1,2,3]"));
    assertThat(gson.fromJson(gson.toJson(i), int[].class), equalTo(i));
    assertThat(gson.fromJson("[1, 2, 3]", int[].class), equalTo(i));
  }

  @Test
  public void user() throws IllegalAccessException {
    Date now = new Date();
    Map<String, Object> data = new HashMap<>();
    data.put(User.Fields.ID, "222");
    data.put(User.Fields.NAME, "henrytao");
    data.put(User.Fields.AGE, 25);
    data.put(User.Fields.CREATED_AT, now.getTime());

    User user = new User();
    user.deserialize(data);

    assertThat(user.getId(), equalTo("222"));
    assertThat(user.getName(), equalTo("henrytao"));
    assertThat(user.getAge(), equalTo(25));
    assertThat(user.getCreateAt().getTime(), equalTo(now.getTime()));

    Map<String, Object> newData = user.serialize();
    assertThat(newData.get(User.Fields.ID), equalTo(data.get(User.Fields.ID)));
    assertThat(newData.get(User.Fields.NAME), equalTo(data.get(User.Fields.NAME)));
    assertThat(newData.get(User.Fields.AGE), equalTo(data.get(User.Fields.AGE)));
    assertThat(newData.get(User.Fields.CREATED_AT), equalTo(data.get(User.Fields.CREATED_AT)));
  }

  static class Cat {

    private int value1 = 1;

    private String value2 = "abc";

    private transient int value3 = 3;

    @Override
    public boolean equals(Object o) {
      Cat cat = (Cat) o;
      return value1 == cat.value1 && StringUtils.equals(value2, cat.value2);
    }
  }

  static class Dog {

  }

}
