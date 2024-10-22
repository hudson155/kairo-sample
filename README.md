# `kairo-sample`

A sample implementation of [Kairo](https://github.com/hudson155/kairo).

This sample project has a single Feature called `LibraryFeature`,
which manages a public library.

There are 3 entities:

- `LibraryBook`
- `LibraryMember`
- `LibraryCard`

A library member can have 0 or more library cards.

## Project information

### Major dependencies

- Gradle 8.10
- Kotlin 2.0
- Java 21
- Kairo 0.23

### Style guide

- **Product terminology:**
  Treat Kairo _Features_ and _Servers_ as proper nouns (the first letter should be capitalized).

See the [style guide](./docs/style-guide.md) for more.

### Chores

See [chores](./docs/chores.md).

## Getting started

**Run the Server:**

```shell
KAIRO_CONFIG=local-development ./gradlew kairo-sample:run
```

**Run checks (tests and lint):**

```shell
./gradlew check
```

**Full build:**

```shell
./gradlew build
```

**Build and run using Shadow:**

```shell
./gradlew kairo-sample:shadowJar
KAIRO_CONFIG=local-development java -jar kairo-sample/build/libs/kairo-sample-shadow.jar
```
