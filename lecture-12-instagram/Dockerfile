FROM gradle:jdk17
# RUN apk add curl
# RUN apk add busybox-extras
VOLUME /data/db
COPY build/libs/spring-app-1.0.jar app.jar
ENTRYPOINT ["java","-jar","/home/gradle/app.jar"]