#!/bin/bash

#mvn clean install -DskipTests
sudo docker rm chitralimbu
sudo docker image remove chitralimbu
sudo docker build -f Dockerfile -t chitralimbu .
sudo docker tag chitralimbu chitralimbu/chitralimbu:latest
sudo docker push chitralimbu/chitralimbu:latest
