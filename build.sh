./gradlew clean :api:bootJar
docker stop concert-ticketing-container
docker rm concert-ticketing-container
docker build -f api/Dockerfile -t  concert-ticketing-api .
docker run -p 8080:8080 --name concert-ticketing-container concert-ticketing-api
