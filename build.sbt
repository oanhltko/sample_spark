// Your sbt build file. Guides on how to write one can be found at
// http://www.scala-sbt.org/0.13/docs/index.html

scalaVersion := "2.11.8"

sparkVersion := "2.1.0"

version := "0.13.5"

spAppendScalaVersion := true

// change the value below to change the directory where your zip artifact will be created
spDistDirectory := target.value

licenses += "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0")

spShortDescription := "My sample Spark Package" // Your one line description of your package

//credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials4")
credentials += Credentials("Sample of Spark package", "spark-packages.org", "oanhltko", "b6492f94a7f723c9dd19d9a9f8ebea3408161bfb")

sparkComponents += "core"

// add any sparkPackageDependencies using sparkPackageDependencies.
// e.g. sparkPackageDependencies += "databricks/spark-avro:0.1"
sparkPackageName := "oanhltko/sample_spark"

spName := "oanhltko/sample_spark" // the name of your Spark Package

pomExtra :=
  <url>https://github.com/oanhltko/sample_spark</url>
  <scm>
    <url>git@github.com:oanhltko/sample_spark.git</url>
    <connection>scm:git:git@github.com:oanhltko/sample_spark.git</connection>
  </scm>
  <developers>
    <developer>
      <id>oanhltko</id>
      <name>Lai Thi Kim Oanh</name>
      <url>https://github.com/oanhltko</url>
    </developer>
  </developers>


