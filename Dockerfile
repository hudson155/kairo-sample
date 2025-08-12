FROM amazoncorretto:21
COPY monolith/build/libs/shadow.jar server.jar
CMD ["java", "-XX:MaxRAMPercentage=80", "-XX:+UseContainerSupport", "-XX:+UseSerialGC", "-XX:+AlwaysActAsServerClassMachine", "-XX:+TieredCompilation", "-XX:TieredStopAtLevel=1", "-Xverify:none", "-XX:+ExitOnOutOfMemoryError", "-Dio.netty.transport.noNative=true", "-jar", "server.jar"]
