FROM amazoncorretto:21
COPY build/libs/shadow.jar server.jar
CMD ["java", "-XX:MaxRAMPercentage=60", "-XX:MaxDirectMemorySize=256m", "-XX:+ExitOnOutOfMemoryError", "-XX:+UseG1GC", "-jar", "server.jar"]
