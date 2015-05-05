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

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import android.os.Parcelable;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
  public void testSerialize_commonModel() throws Exception {
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
  public void testSerialize_notNullFieldModel_failed() throws Exception {
    NotNullFieldModel model = new NotNullFieldModel();
    model.setAge(26);
    model.serialize();
  }

  @Test
  public void testSerialize_notNullFieldModel_success() throws Exception {
    NotNullFieldModel model = new NotNullFieldModel();
    model.setName("henry");
    model.setAge(26);
    Map<String, Object> map = model.serialize();
    assertThat(map.size(), equalTo(2));
    assertThat(map.get(CommonModel.Fields.NAME), equalTo("henry"));
    assertThat(map.get(CommonModel.Fields.AGE), equalTo(26));
    assertThat(map.get(CommonModel.Fields.CREATED_AT), nullValue());
  }

  @Test
  public void testSerialize_nestedModel() throws Exception {
    long time = new GregorianCalendar(2015, 05, 04).getTimeInMillis();
    CommonModel commonModel = new CommonModel("common-henry", 26, new Date(time));
    NestedModel nestedModel = new NestedModel("nested-henry", 25, new Date(time), commonModel);
    Map<String, Object> map = nestedModel.serialize();
    assertThat(map.size(), equalTo(4));
    assertThat(map.get(NestedModel.Fields.NAME), equalTo("nested-henry"));
    assertThat(map.get(NestedModel.Fields.AGE), equalTo(25));
    assertThat(map.get(NestedModel.Fields.CREATED_AT), equalTo(time));

    Map<String, Object> commonRes = (Map<String, Object>) map.get(NestedModel.Fields.NESTED);
    assertThat(commonRes.size(), equalTo(3));
    assertThat(commonRes.get(CommonModel.Fields.NAME), equalTo("common-henry"));
    assertThat(commonRes.get(CommonModel.Fields.AGE), equalTo(26));
    assertThat(commonRes.get(CommonModel.Fields.CREATED_AT), equalTo(time));
  }

  @Test
  public void testDeserialize_commonModel() throws Exception {
    long time = new GregorianCalendar(2015, 05, 04).getTimeInMillis();
    Map<String, Object> map = new HashMap<>();
    map.put(CommonModel.Fields.NAME, "henry");
    map.put(CommonModel.Fields.AGE, 26);
    map.put(CommonModel.Fields.CREATED_AT, time);

    CommonModel model = new CommonModel();
    model.deserialize(map);
    assertThat(model.getName(), equalTo("henry"));
    assertThat(model.getAge(), equalTo(26));
    assertThat(model.getCreateAt(), equalTo(new Date(time)));
  }

  @Test(expected = DeserializerException.class)
  public void testDeserialize_notNullFieldModel_failed() throws Exception {
    long time = new GregorianCalendar(2015, 05, 04).getTimeInMillis();
    Map<String, Object> map = new HashMap<>();
    map.put(NotNullFieldModel.Fields.AGE, 26);
    map.put(NotNullFieldModel.Fields.CREATED_AT, time);

    NotNullFieldModel model = new NotNullFieldModel();
    model.deserialize(map);
  }

  @Test
  public void testDeserialize_notNullFieldModel_success() throws Exception {
    long time = new GregorianCalendar(2015, 05, 04).getTimeInMillis();
    Map<String, Object> map = new HashMap<>();
    map.put(NotNullFieldModel.Fields.NAME, "henry");
    map.put(NotNullFieldModel.Fields.AGE, 26);
    map.put(NotNullFieldModel.Fields.CREATED_AT, time);

    NotNullFieldModel model = new NotNullFieldModel();
    model.deserialize(map);
    assertThat(model.getName(), equalTo("henry"));
    assertThat(model.getAge(), equalTo(26));
    assertThat(model.getCreateAt(), equalTo(new Date(time)));
  }

  @Test
  public void testDeserialize_nestedModel() throws Exception {
    long time = new GregorianCalendar(2015, 05, 04).getTimeInMillis();
    Map<String, Object> commonMap = new HashMap<>();
    commonMap.put(CommonModel.Fields.NAME, "henry");
    commonMap.put(CommonModel.Fields.AGE, 26);
    commonMap.put(CommonModel.Fields.CREATED_AT, time);

    Map<String, Object> nestedMap = new HashMap<>();
    nestedMap.put(NestedModel.Fields.NAME, "henry-nested");
    nestedMap.put(NestedModel.Fields.AGE, 25);
    nestedMap.put(NestedModel.Fields.CREATED_AT, time);
    nestedMap.put(NestedModel.Fields.NESTED, commonMap);

    NestedModel model = new NestedModel();
    model.deserialize(nestedMap);
    assertThat(model.getName(), equalTo("henry-nested"));
    assertThat(model.getAge(), equalTo(25));
    assertThat(model.getCreateAt(), equalTo(new Date(time)));
    assertThat(model.getNested().getName(), equalTo("henry"));
    assertThat(model.getNested().getAge(), equalTo(26));
    assertThat(model.getNested().getCreateAt(), equalTo(new Date(time)));
  }
}
