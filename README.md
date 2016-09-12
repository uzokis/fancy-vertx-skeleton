# About

This is a [Vert.x](http://vertx.io/) skeleton project with some basics pre-setup:

  - [Gradle](https://gradle.org/) as build tool
  - [Shadow](http://imperceptiblethoughts.com/shadow/) plugin for fat jar creation
  - [JUL](https://docs.oracle.com/javase/8/docs/technotes/guides/logging/overview.html) starting point
  - [Guice](https://github.com/google/guice) for dependency injection
  - An example verticle & accompanying integration test using [WireMock](http://wiremock.org/)

# Getting started

  - `gradlew eclipse` - generates project files to import the project into eclipse (Edit [build.gradle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/build.gradle) if you want another IDE)
  - `gradlew runjar` - convencience task to run the Vert.x project (Edit [build.gradle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/build.gradle) if you want to adjust the arguments)
  - `gradlew clean build -x test` - clean & build the project, don't run the tests

# What to find where

  - [build.gradle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/build.gradle) - Gradle build fild
  - [logger.properties](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/logger.properties) - JUL config
  - [MainVerticle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/java/eu/fancybrackets/template/verticle/MainVerticle.java)
  - [ConfigModule](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/java/eu/fancybrackets/template/guice/ConfigModule.java) - Provides some injection bindings, loads [the given config.json](http://vertx.io/blog/vert-x-application-configuration/) or the one packaged into the project 
  - [RonSwansonVerticle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/java/eu/fancybrackets/template/verticle/RonSwansonVerticle.java) & [RonSwansonHandler](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/java/eu/fancybrackets/template/handler/RonSwansonHandler.java) - example verticle and helper class
  - [config.json](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/resources/config.json) - packaged config.json to be used as default
  - [RonSwansonIntegrationTest](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/test/java/tests/eu/fancybrackets/template/RonSwansonIntegrationTest.java) & [GuiceTestModule](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/test/java/tests/eu/fancybrackets/template/GuiceTestModule.java) - integration test & guice test module to startup a vertx instance with a mock config

