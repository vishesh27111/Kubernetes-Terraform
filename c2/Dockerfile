FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# COPY . .

# RUN mkdir -p ~/c2/test

COPY ./target/c2-0.0.1-SNAPSHOT.jar container2.jar

EXPOSE 8080

CMD ["java","-jar","container2.jar"]