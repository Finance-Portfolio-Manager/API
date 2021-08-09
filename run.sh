#!/bin/sh
docker stop finportman-api
# . ./.env
mvn clean package -Dmaven.test.skip=true || exit 1
docker build -t dev/finportman-api . || exit 1
docker run \
    -d \
    --rm \
    --network="host" \
    --name finportman-api \
    dev/finportman-api
docker rmi -f $(docker images -f "dangling=true" -q)
