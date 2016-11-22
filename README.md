# About

This is a [Vert.x](http://vertx.io/) skeleton project with some basics pre-setup (most of them could be easily swapped):

  - [Gradle](https://gradle.org/) as build tool
  - [Shadow](http://imperceptiblethoughts.com/shadow/) plugin for fat jar creation
  - [JUL](https://docs.oracle.com/javase/8/docs/technotes/guides/logging/overview.html) starting point
  - [Guice](https://github.com/google/guice) for dependency injection
  - [JOOQ](http://www.jooq.org/) as persistence layer (configured for CockroachDB/PostgreSQL)
  - An example verticle & accompanying integration test using [WireMock](http://wiremock.org/)

[![Build Status](https://travis-ci.org/uzokis/fancy-vertx-skeleton.svg?branch=master)](https://travis-ci.org/uzokis/fancy-vertx-skeleton)

# Getting started

  - `gradlew eclipse` - generates project files to import the project into eclipse (Edit [build.gradle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/build.gradle) if you want another IDE)
  - `gradlew clean build -x test` - clean & build the project, don't run the tests
  - `gradlew runJar` - convencience task to run the Vert.x project (Edit [build.gradle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/build.gradle) if you want to adjust the arguments)
  - After `runJar` navigate to http://localhost:8080/swanson to see an awesome [Ron Swanson quote](https://github.com/jamesseanwright/ron-swanson-quotes)
  - `gradlew generateJooq` - convencience task to run the JOOQ code-generator (Edit [jooq-config.xml](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/jooq-config.xml) to adjust behaviour)

# What to find where

  - [build.gradle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/build.gradle) - Gradle build file
  - [MainVerticle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/java/eu/fancybrackets/template/verticle/MainVerticle.java) - The verticle used by the Launcher in the JAR manifest (Edit [build.gradle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/build.gradle) to specify another one)
  - [ConfigModule](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/java/eu/fancybrackets/template/guice/ConfigModule.java) - Provides some injection bindings, loads [the given config.json](http://vertx.io/blog/vert-x-application-configuration/) or the one packaged into the project
  - [config.json](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/resources/config.json) - packaged config.json to be used as default
  - [RonSwansonVerticle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/java/eu/fancybrackets/template/verticle/RonSwansonVerticle.java) & [RonSwansonHandler](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/main/java/eu/fancybrackets/template/handler/RonSwansonHandler.java) - example verticle and helper class
  - [RonSwansonIntegrationTest](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/test/java/tests/eu/fancybrackets/template/RonSwansonIntegrationTest.java) & [GuiceTestModule](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/src/test/java/tests/eu/fancybrackets/template/GuiceTestModule.java) - integration test & guice test module to startup a vertx instance with a mock config
  - [logger.properties](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/logger.properties) - JUL config (Used in the `runJar` task in [build.gradle](https://github.com/uzokis/fancy-vertx-skeleton/blob/master/build.gradle))
