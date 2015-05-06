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

package me.henrytao.sharewifi.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.henrytao.sharewifi.model.orm.BaseModel;
import me.henrytao.sharewifi.model.orm.Column;

/**
 * Created by henrytao on 3/31/15.
 */
@Accessors(prefix = "m")
public class UserModel extends BaseModel<UserModel> {

  @Column(name = UserModel.Fields.AGE)
  @Getter @Setter private int mAge;

  @Column(name = UserModel.Fields.CREATED_AT)
  @Getter @Setter private Date mCreateAt;

  @Column(name = UserModel.Fields.NAME)
  @Getter @Setter private String mName;

  public interface Fields extends BaseModel.Fields {

    final String AGE = "age";
    final String CREATED_AT = "created_at";
    final String NAME = "name";
  }

}
