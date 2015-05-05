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

package me.henrytao.sharewifi.model.orm.benchmark;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import android.os.Bundle;
import android.os.Parcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import me.henrytao.sharewifi.RobolectricGradleTestRunner;
import me.henrytao.sharewifi.util.JsonUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by henrytao on 5/5/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 21)
public class BenchMarkTest {

  private static final String MODEL = "model";

  private static final int LOOP = 10000;

  class Counter {

    private Timer mTimer;

    private int mCount;

    private int mInterval = 1;

    public Counter() {
      mTimer = new Timer();
    }

    public Counter(int interval) {
      this();
      mInterval = interval;
    }

    public int getCount() {
      return mCount;
    }

    public void start() {
      mCount = 0;
      mTimer.schedule(new TimerTask() {
        @Override
        public void run() {
          mCount += 1;
        }
      }, 0, mInterval);
    }

    public void stop() {
      mTimer.cancel();
    }
  }

  @Test
  public void testTargetModel() throws Exception {
    long time = new Date().getTime();
    TargetModel model = new TargetModel("henry", 26, time);
    Counter counter = new Counter();

    counter.start();
    for (int i = 0; i < LOOP; i++) {
      Bundle bundle = new Bundle();
      bundle.putString(MODEL, JsonUtils.encode(model.serialize()));

      TargetModel target = new TargetModel().deserialize(bundle.getString(MODEL));
      assertThat(target.getName(), equalTo("henry"));
      assertThat(target.getAge(), equalTo(26));
      assertThat(target.getCreatedAt(), equalTo(time));
    }
    counter.stop();
    System.out.println("testTargetModel: " + counter.getCount());
  }

  @Test
  public void testParcelableModel() throws Exception {
    long time = new Date().getTime();
    ParcelableModel model = new ParcelableModel("henry", 26, time);
    Counter counter = new Counter();

    counter.start();
    for (int i = 0; i < LOOP; i++) {
      Bundle bundle = new Bundle();
      bundle.putParcelable(MODEL, model);

      Parcel parcel = Parcel.obtain();
      bundle.writeToParcel(parcel, 0);
      parcel.setDataPosition(0);
      bundle = parcel.readBundle();

      ParcelableModel target = bundle.getParcelable(MODEL);
      assertThat(target.getName(), equalTo("henry"));
      assertThat(target.getAge(), equalTo(26));
      assertThat(target.getCreatedAt(), equalTo(time));
    }
    counter.stop();
    System.out.println("testParcelableModel: " + counter.getCount());
  }

  @Test
  public void testSerializableModel() throws Exception {
    long time = new Date().getTime();
    SerializableModel model = new SerializableModel("henry", 26, time);
    Counter counter = new Counter();

    counter.start();
    for (int i = 0; i < LOOP; i++) {
      Bundle bundle = new Bundle();
      bundle.putSerializable(MODEL, model);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(model);
      oos.close();
      byte[] bytes = baos.toByteArray();
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bais);
      model = (SerializableModel) ois.readObject();

      SerializableModel target = (SerializableModel) bundle.getSerializable(MODEL);
      assertThat(target.getName(), equalTo("henry"));
      assertThat(target.getAge(), equalTo(26));
      assertThat(target.getCreatedAt(), equalTo(time));
    }
    counter.stop();
    System.out.println("getSerializableModel: " + counter.getCount());
  }

}
