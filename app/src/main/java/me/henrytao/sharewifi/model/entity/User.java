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

package me.henrytao.sharewifi.model.entity;

import com.google.gson.Gson;

import java.util.Date;

import me.henrytao.sharewifi.model.orm.BaseEntity;
import me.henrytao.sharewifi.model.orm.Column;

/**
 * Created by henrytao on 3/31/15.
 */
public class User extends BaseEntity<User> {

  private static Gson GSON = new Gson();

  @Column(name = Fields.NAME)
  private String mName;

  @Column(name = Fields.AGE)
  private int mAge;

  @Column(name = Fields.CREATED_AT)
  private Date mCreateAt;

  public User() {
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public int getAge() {
    return mAge;
  }

  public void setAge(int age) {
    mAge = age;
  }

  public Date getCreateAt() {
    return mCreateAt;
  }

  public void setCreateAt(Date createAt) {
    mCreateAt = createAt;
  }

  public interface Fields extends BaseEntityColumns {

    final String NAME = "name";

    final String AGE = "age";

    final String CREATED_AT = "created_at";
  }

}
