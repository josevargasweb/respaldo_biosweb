#!/bin/bash

docker build -t bioslisdb -f database/database.Dockerfile .
docker rmi $(docker images -qa -f 'dangling=true') -f
docker run -d -it --name bioslisdb -p 1521:1521 -p 5500:5500 bioslisdb

while [ $( docker container inspect -f '{{json .State.Health.Status}}' bioslisdb ) != "\"healthy\"" ]
do
  echo  Database status is: $( docker container inspect -f '{{json .State.Health.Status}}' bioslisdb  )
  sleep 10
done