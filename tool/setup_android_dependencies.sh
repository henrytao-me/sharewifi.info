#!/usr/bin/env bash

export ANDROID_HOME=/Volumes/iMac/workspace/android/bundle/sdk

echo y | $ANDROID_HOME/tools/android update sdk --no-ui --all --filter \
tools,\
platform-tools,\
build-tools-21.1.1

echo y | $ANDROID_HOME/tools/android update sdk --no-ui --all --filter

#echo y | $ANDROID_HOME/tools/android update sdk --no-ui --all --filter \
#    extra-android-m2repository,\
#    extra-android-support,\
#    extra-google-admob_ads_sdk,\
#    extra-google-analytics_sdk_v2,\
#    extra-google-google_play_services_froyo,\
#    extra-google-google_play_services,\
#    extra-google-m2repository
#
#echo y | $ANDROID_HOME/tools/android update sdk --no-ui --all --filter \
#  android-21