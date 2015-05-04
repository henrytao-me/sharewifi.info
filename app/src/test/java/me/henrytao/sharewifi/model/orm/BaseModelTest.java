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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import me.henrytao.sharewifi.RobolectricGradleTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by henrytao on 5/3/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 21)
public class BaseModelTest {

  @Test
  public void testSerialize_noNotNullField_success() throws Exception {
    long time = new GregorianCalendar(2015, 05, 04).getTimeInMillis();
    CommonModel model = new CommonModel("henry", 26, new Date(time));
    Map<String, Object> map = model.serialize();
    assertThat(map.size(), equalTo(3));
    assertThat(map.get(CommonModel.Fields.NAME), equalTo("henry"));
    assertThat(map.get(CommonModel.Fields.AGE), equalTo(26));
    assertThat(map.get(CommonModel.Fields.CREATED_AT), equalTo(time));

    model = new CommonModel();
    model.setName("henry");
    model.setAge(26);
    map = model.serialize();
    assertThat(map.size(), equalTo(2));
    assertThat(map.get(CommonModel.Fields.NAME), equalTo("henry"));
    assertThat(map.get(CommonModel.Fields.AGE), equalTo(26));
    assertThat(map.get(CommonModel.Fields.CREATED_AT), nullValue());
  }

  @Test(expected = SerializerException.class)
  public void testSerialize_hasNotNullField_failed() throws Exception {
    NotNullFieldModel model = new NotNullFieldModel();
    model.setAge(26);
    model.serialize();
  }

  @Test
  public void testSerialize_hasNotNullField_success() throws Exception {
    NotNullFieldModel model = new NotNullFieldModel();
    model.setName("henry");
    model.setAge(26);
    Map<String, Object> map = model.serialize();
    assertThat(map.size(), equalTo(2));
    assertThat(map.get(CommonModel.Fields.NAME), equalTo("henry"));
    assertThat(map.get(CommonModel.Fields.AGE), equalTo(26));
    assertThat(map.get(CommonModel.Fields.CREATED_AT), nullValue());
  }
}
