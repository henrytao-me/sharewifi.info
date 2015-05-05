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

package me.henrytao.sharewifi.model.orm;

import java.util.Date;

/**
 * Created by henrytao on 5/3/15.
 */
public class CommonModel extends BaseModel<CommonModel> {

  public interface Fields extends BaseModel.Fields {

    final String NAME = "name";

    final String AGE = "age";

    final String CREATED_AT = "created_at";
  }

  @Column(name = Fields.NAME)
  private String mName;

  @Column(name = Fields.AGE)
  private int mAge;

  @Column(name = Fields.CREATED_AT)
  private Date mCreateAt;

  public CommonModel() {

  }

  public CommonModel(String name, int age, Date createAt) {
    mName = name;
    mAge = age;
    mCreateAt = createAt;
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

}