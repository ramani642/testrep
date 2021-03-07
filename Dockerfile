FROM openjdk:11
ADD target/divvyDose-0.0.1-SNAPSHOT.jar divvyDose-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "divvyDose-0.0.1-SNAPSHOT.jar"]