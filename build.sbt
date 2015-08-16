name := "swap-swap"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  ("com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0").
                        exclude("org.seleniumhq.selenium", "jetty-repacked"),
  "org.scalatest" %% "scalatest" % "latest.integration" % "test",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "org.slf4j" % "slf4j-simple" % "1.7.12",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "org.mongodb" %% "casbah" % "2.8.2"
)
