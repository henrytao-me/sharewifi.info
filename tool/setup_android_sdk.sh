#!/usr/bin/env bash

sudo apt-get update -qq
if [ `uname -m` = x86_64 ]; then
  sudo apt-get install -qq --force-yes libgd2-xpm ia32-libs ia32-libs-multiarch lib32stdc++6 lib32z1 > /dev/null;
fi

wget http://dl.google.com/android/android-sdk_r24.1.2-linux.tgz
tar -zxf android-sdk_r24.1.2-linux.tgz

export ANDROID_HOME=`pwd`/android-sdk-linux
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools

android list sdk --all --extended
FILTER=tools,platform-tools,extra-android-support,build-tools-22.0.1,android-22
yes | android update sdk --no-ui --all --filter $FILTER
yes | $ANDROID_HOME/tools/android update sdk --no-ui --all --filter ${FILTER}

echo "sdk.dir=$ANDROID_HOME" > local.properties