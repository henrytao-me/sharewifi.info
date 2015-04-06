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

echo y | $ANDROID_HOME/tools/android update sdk --no-ui --all --filter "tools,platform-tools"
echo y | $ANDROID_HOME/tools/android update sdk --no-ui --all --filter "android-22,build-tools-22.0.1"
echo y | $ANDROID_HOME/tools/android update sdk --no-ui --all --filter "extra-android-support"

echo "sdk.dir=$ANDROID_HOME" > local.properties