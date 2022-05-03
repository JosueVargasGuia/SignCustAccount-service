FROM openjdk:11
EXPOSE  8089
WORKDIR /app
ADD   ./target/*.jar /app/SignCustAccount-service.jar
ENTRYPOINT ["java","-jar","/app/SignCustAccount-service.jar"]