FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# COPY . .

COPY ./target/c1-0.0.1-SNAPSHOT.jar container1.jar
# RUN mvn clean package -DskipTests

EXPOSE 6000

CMD ["java","-jar","container1.jar"]