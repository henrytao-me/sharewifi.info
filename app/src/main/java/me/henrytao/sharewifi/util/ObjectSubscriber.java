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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by henrytao on 5/5/15.
 */
public class ObjectSubscriber implements Observable.OnSubscribe<Object> {

  private static Map<String, Map<UUID, Subscriber>> subscribers;

  public static void push(String key, Object object) {
    Map<UUID, Subscriber> list = subscribers.get(key);
    for (Subscriber subscriber : list.values()) {
      subscriber.onNext(object);
    }
  }

  private final UUID mID;

  private final String mKey;

  public ObjectSubscriber(String key) {
    mKey = key;
    mID = UUID.randomUUID();
  }

  @Override
  public void call(Subscriber<? super Object> subscriber) {
    Map<UUID, Subscriber> listSubscriber = subscribers.get(mKey);
    if (listSubscriber == null) {
      listSubscriber = new HashMap<>();
      subscribers.put(mKey, listSubscriber);
    }
    listSubscriber.put(mID, subscriber);

    Subscription subscription = Subscriptions.create(() -> {
      Map<UUID, Subscriber> list = subscribers.get(mKey);
      if (list != null) {
        list.remove(mID);
      }
    });
    subscriber.add(subscription);
  }
}
