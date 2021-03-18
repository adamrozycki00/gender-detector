FROM openjdk:11.0.10-oraclelinux7

MAINTAINER Adam Różycki "adamrozycki00@gmail.com"

EXPOSE 8080

WORKDIR /usr/local/bin/

COPY ./target/gender-detector-0.0.1-SNAPSHOT.jar gender-detector.jar

CMD ["java", "-jar", "gender-detector.jar"]