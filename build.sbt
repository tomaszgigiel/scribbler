lazy val root = project
  .in(file("."))
  .settings(
    name := "scribbler-sbt",
    organization := "pl.tomaszgigiel",
    version := "1000-SNAPSHOT",
    scalaVersion := scala3Version,
    scalacOptions := Seq(
      "-indent",
      "-new-syntax",
      "-explain"),
    licenses := List("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    libraryDependencies ++= Seq(catsCore, catsEffect, commonsCollections4, commonsIO, commonsLang3, doobieCore, doobieH2, doobieHikari, doobieScalatest, freemarker, junitJupiter, log4jCore, logbackClassic, lombok, munitCatsEffect3, scalaLogging, scalatest, stanfordCoreNLP, typesafeConfig),
    Compile / mainClass := Some("pl.tomaszgigiel.scribbler.ScalaApp"),
    assembly / assemblyJarName := s"${name.value}-uberjar.jar",
    assembly / assemblyMergeStrategy := {
      case PathList(ps@_*) if ps.last == "module-info.class" => MergeStrategy.discard
      case x =>
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
    }
  )
val scala3Version = "3.1.2"
val catsCore = "org.typelevel" %% "cats-core" % "2.7.0"
val catsEffect = "org.typelevel" %% "cats-effect" % "3.4-389-3862cf0"
val commonsCollections4 = "org.apache.commons" % "commons-collections4" % "4.4"
val commonsIO = "commons-io" % "commons-io" % "2.11.0"
val commonsLang3 = "org.apache.commons" % "commons-lang3" % "3.12.0"
val doobieCore = "org.tpolecat" %% "doobie-core" % "1.0.0-RC2"
val doobieH2 = "org.tpolecat" %% "doobie-h2" % "1.0.0-RC2"
val doobieHikari = "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC2"
val doobieScalatest = "org.tpolecat" %% "doobie-scalatest" % "1.0.0-RC1" % Test
val freemarker = "org.freemarker" % "freemarker" % "2.3.31"
val junitJupiter = "org.junit.jupiter" % "junit-jupiter" % "5.8.2" % Test
val log4jCore = "org.apache.logging.log4j" % "log4j-core" % "2.17.1"
val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.11"
val lombok = "org.projectlombok" % "lombok" % "1.18.22" % Provided
val munitCatsEffect3 = "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
val scalatest = "org.scalatest" %% "scalatest" % "3.2.11" % Test
val stanfordCoreNLP = "edu.stanford.nlp" % "stanford-corenlp" % "4.4.0"
val typesafeConfig = "com.typesafe" % "config" % "1.4.2"
