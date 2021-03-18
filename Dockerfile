FROM openjdk:11.0.10-oraclelinux7

MAINTAINER Adam Różycki "adamrozycki00@gmail.com"

EXPOSE 8080

WORKDIR /usr/local/bin/

COPY ./target/gender-detector-0.0.1-SNAPSHOT.jar gender-detector.jar
COPY ./src/main/resources/repositories/female.txt ./src/main/resources/repositories/female.txt
COPY ./src/main/resources/repositories/male.txt ./src/main/resources/repositories/male.txt

CMD ["java", "-jar", "gender-detector.jar"]