# Build plugins

The following Gradle plugins help standardize the build process across Gradle modules.

- `kairo-sample`: Configures JVM Gradle modules.
  Unless and until multiplatform modules are introduced,
  this should be used in all Gradle modules.
- `kairo-sample-server`: This plugin should be applied to Server implementations.
  It configures the JVM application, and uses the Shadow plugin to create a shaded (fat) JAR file
  so that the application can be run from a single file.
