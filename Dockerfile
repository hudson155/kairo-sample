FROM amazoncorretto:21
COPY build/libs/kairo-sample-shadow.jar server.jar
CMD ["java", "-XX:MaxRAMPercentage=80", "-XX:+UseContainerSupport", "-XX:+UseSerialGC", "-XX:+AlwaysActAsServerClassMachine", "-XX:+TieredCompilation", "-XX:TieredStopAtLevel=1", "-Xverify:none", "-XX:+ExitOnOutOfMemoryError", "-Dio.netty.transport.noNative=true", "-Dlog4j2.debug=true", "-jar", "server.jar"]
