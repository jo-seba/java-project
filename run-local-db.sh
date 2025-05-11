#!/bin/bash

# 변수 설정
DOCKERFILE_PATH=database/Dockerfile
IMAGE_NAME=concert-ticketing-database
CONTAINER_NAME=concert-ticketing-database-container

# 컨테이너 존재 시 삭제
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Container $CONTAINER_NAME is already running. Stopping and removing it..."

    docker stop $CONTAINER_NAME

    docker rm $CONTAINER_NAME
fi

# 빌드
echo "Building MySQL image.."
docker build -f $DOCKERFILE_PATH -t $IMAGE_NAME .

# 실행
echo "Running MySQL container"
docker run -p 3306:3306 --name $CONTAINER_NAME $IMAGE_NAME

echo "MySQL container $CONTAINER_NAME is running"
