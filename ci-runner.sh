#!/bin/bash
cd "`dirname $0`"
set -e


if [ "$USE_IMAGE" = "" ];then export USE_IMAGE="riccardoblb/buildenvs:javagl"; fi

RUN_AS=""
userUID=""
groupUID=""

# Use non sudo user in docker, this will preserve files' permissions
if [ "$SUDO_USER" != "" ];
then
    userUID=`id -u $SUDO_USER`
    groupUID=`id -g $SUDO_USER`
    RUN_AS="-u=$userUID:$groupUID"
else
    userUID=`id -u`
    groupUID=`id -g`
    RUN_AS=""
fi

if [ "$CURRENT_GRADLE_HOME"="" ];
then
    cuserRoot=""
    if [ "$SUDO_USER" != "" ];
    then
        cuserRoot=`eval echo "~$SUDO_USER"`
    else
        cuserRoot=`eval echo "~"`
    fi
    export CURRENT_GRADLE_HOME="$cuserRoot/.gradle"
fi
echo "Found current user gradle home $CURRENT_GRADLE_HOME"
echo "Mount $CURRENT_GRADLE_HOME/init.d in container"

mkdir -p "$CURRENT_GRADLE_HOME/init.d/"
mkdir -p "build.cache/init.d/"
if [ "$SUDO_USER" != "" ];
then
    chown $userUID:$groupUID -Rf "build.cache"
fi

RUNTIME="sudo docker"
if [ "`which podman`" != "" ];then   RUNTIME="podman"; fi

ARGS=""
if [ "$NO_INTERRACTIVE" = "" ];
then
    ARGS="$ARGS -it"
fi

$RUNTIME pull "$USE_IMAGE"
cmd="$RUNTIME run -v\"$PWD:/workdir\" -eGRADLE_USER_HOME=/workdir/build.cache \
-v\"$CURRENT_GRADLE_HOME/init.d:/workdir/build.cache/init.d\" \
-w /workdir $RUN_AS --rm $ARGS $USE_IMAGE ./gradlew $@"
echo $cmd
eval $cmd
