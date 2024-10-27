### Build
cd rabbitmq-with-stomp-docker && docker build -t my-rabbitmq .
./gradlew build && docker-compose build && docker-compose up