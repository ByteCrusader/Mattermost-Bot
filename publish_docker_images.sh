#!/bin/bash

echo "/___/ Start work with service's gradle builds /___/"
./gradlew clean
./gradlew build
echo "/___/ Start work with service's docker images /___/"
cd services
ls -s
echo "/___/ Start work with BFF service /___/ /___/ /___/"
cd bricklayer-bff
docker build -t bytecrusader/bricklayer-bff .
docker push bytecrusader/bricklayer-bff
echo "/___/ Work with BFF service has finished /___/ /___/"
cd ..
cd bricklayer-constructor
echo "/___/ Start work with CONSTRUCTOR service /___/ /___/"
docker build -t bytecrusader/bricklayer-constructor .
docker push bytecrusader/bricklayer-constructor
echo "/___/ Work with CONSTRUCTOR service has finished /___/"
cd ..
echo "/___/ Start work with ENGINE service /___/ /___/ /___/"
cd bricklayer-engine
docker build -t bytecrusader/bricklayer-engine .
docker push bytecrusader/bricklayer-engine
echo "/___/ Work with ENGINE service has finished /___/ /___/"
cd ..
echo "/___/ Start work with STORAGE service /___/ /___/ /___/"
cd bricklayer-storage
docker build -t bytecrusader/bricklayer-storage .
docker push bytecrusader/bricklayer-storage
echo "/___/ Work with STORAGE service has finished /___/ /___/"
cd ..
echo "/___/ Start work with USER service /___/ /___/ /___/ /___/"
cd bricklayer-user
docker build -t bytecrusader/bricklayer-user .
docker push bytecrusader/bricklayer-user
echo "/___/ Work with USER service has finished /___/ /___/ /___/"
cd ..
ls -s