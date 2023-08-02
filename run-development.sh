#!/bin/bash

# mvn clean package
docker build -t bioslis .
docker rmi $(docker images -qa -f 'dangling=true') -f
docker run -it --rm -p 8081:8080 --name bioslis_instance -d bioslis

if ! command -v COMMAND &> /dev/null
then
    echo "Application is runnig en the following url:"
    echo "http://127.0.0.1:8081/BiosLis30-0.3/Microbiologia"
#     exit
else
    open http://127.0.0.1:8081/BiosLis30-0.3/Microbiologia
fi

docker exec -it bioslis_instance /bin/bash
docker stop bioslis_instance
