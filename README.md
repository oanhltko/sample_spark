# Publishing your Spark Package in the Spark Packages repository
## Overview

This section describes how to publish a package to Spark Package Repository using `"sbt-spark-package"` plugin (for package contains scala or java and python code).

## Environment
Library building and program execution have been checked in the following environment:

  - OS: CentOS 6.9(x64).
  - Java: JDK 1.8.0_191.
  - Scala: 2.11.8
  - Spark: 2.1.0
  - sbt: 0.13.6

## Preparing software component
At first, we will prepare resource following directory structure. Please note that the standard directory layout of one scala/java project is:
-	src/main/java/...: include main Java sources.
-	src/main/scala/...: inlude main Scala sources.
-	src/main/resources/...: contains files to include in main jar.
...

So, the directory structure will be placed as below:
- Directory structure

    |-- build.sbt
    
    |-- lib
    
    |-- project
    
    |-- src
    
    |...|-- main
    
    |...|...|---- java
    
    |...|...|---- resources
    
    |...|...|---- scala
    
    |-- target

## Preparations

### 1. Prepare source code on GitHub server

- Step 1: Sign in GitHub by your account.
- Step 2: Create a repository.
- Step 3: Push your source code to this repository. Here is my sample repository: https://github.com/oanhltko/sample_spark

### 2. Add sbt-spark-package plugin to build
We can use sbt-spark-package plugin to publish the package which contains any Java sources, Scala sources and Python sources.
About sbt-spark-package, please refer to https://github.com/databricks/sbt-spark-package.
To add this plugin to your source, please open <your_project>/project/plugins.sbt file, then add code in below:

```scala
  resolvers += "bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven/"
  addSbtPlugin("org.spark-packages" % "sbt-spark-package" % "0.2.6")
```

### 3. Specify sbt version
Open <your_project>/project/build.properties file, add code in below to define the sbt version:
- `sbt.version=0.13.6`

### 4. Make build.sbt file
The GitHub repository name, the release version, ... are declared in build.sbt (its name can be something, but extension is `.sbt`).

There are some required information that you must add to build.sbt:
- Name of your Spark Package:

`spName := "organization/my-awesome-spark-package"`

My sample: 

`spName := "oanhltko/sample_spark"`

- Spark Version your package depends on

`sparkVersion := "x.x.x"`

My sample: 

`sparkVersion := "2.1.0"`

- Define release version

`version := "x.x.x"`

Note: The release version cannot contain -SNAPSHOT. SNAPSHOT version is not allowed to publish to Spark Package repository.

- Define location which contains zip/jar/pom file which are created:

`spDistDirectory := target.value`

If you define `target.value`, the zip file will be placed in <your_project>/target/ folder.

- Declare the license that is used in your project. Example:

`licenses += "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0")`

To register/publish package, you have add short description, description, homepage and credential informations.

- Add short description: 

`spShortDescription := "<Your one line description of your package>"`

- Add description:

```scala
    spDescription := """<My long description.
                    |Could be multiple lines long.
                    | - My package can do this,
                    | - My package can do that.>""".stripMargin
```

- Add credential: 

`credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials")`

Open <user_home>/.ivy2/.sbtcredentials file, then add code in below to define the credential information:

````scala
realm=Spark Packages Realm
host=spark-packages.org
user=<username> // Fill your GitHub username 
password=<personal_access_token_of_GitHub_account>
````
To get <personal_access_token_of_GitHub_account>, please refer to https://help.github.com/articles/creating-a-personal-access-token-for-the-command-line/.

Note: During generate access token key, when define access scopes for personal tokens, you must choose only `read:org` access right in `admin:org` section to authenticate ownership of the repo.

- Declare the homepage:

`spHomepage := // Set this if you want to specify a web page other than your github repository.`

My sample: 

`spHomepage := https://github.com/oanhltko/sample_spark`

- Define contents for making pom: You can define some information for making pom.xml file by:

`pomExtra := // Add your pom contents`

My sample:

```scala
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
```

## Publishing steps

The Release Artifact is a zip file that includes a `jar` file and a `pom` file. The name of the artifact must be in the format `$GITHUB_REPO_NAME-$VERSION.zip`. Similarly, the name of the jar and the pom must be `$GITHUB_REPO_NAME-$VERSION.jar` and `$GITHUB_REPO_NAME-$VERSION.pom` respectively. 

Example:

- My GitHub package repository is https://github.com/oanhltko/sample_spark, release version is 1.0.0, my artifact is: `sample_spark-1.0.0.zip`. This zip file contains `sample_spark-1.0.1.jar` and `sample_spark-1.0.1.pom` files.

### 1. Generate jar file

To generate jar file, run the command

    $cd <your_project>

    $sbt spPackage

### 2. Generate pom file

To generate pom file, run the command:

    $sbt spMakePom

### 3. Generate zip file

To generate pom file, run the command:

    $sbt spDist

### 4. Register a package in Spark Package repository

There are 2 ways to register a package to Spark Package Repository.

##### Option 1: Manual register

- Step 1: Go to https://spark-packages.org/register, login by GitHub account and fill the information of package.
-- name: Select a package from your GitHub repository
-- short description: Short description about package
-- description: Description about the package
-- homepage: Url to your package in GitHub repository (It is automatically filled after you select package from GitHub repository)

- Step 2: Click on "Submit" button. After that, your package is submitted for verification. Please wait confirmation from administrator (It maybe take a time).

##### Option 2: Register by command

Run the command in below to register the package:

    $sbt spRegister

### 5. Publish package to Spark Package repository

Run the command to publish:

    $sbt spPublish

The release tag to browse source code is the latest commit ID before publishing package.

After run this command, you can see the information:

```
SUCCESS: Successfully submitted release for validation. You may follow your progress on http://spark-packages.org/staging?id=<xxxx>
[success] Total time: 2 s, completed Dec 4, 2018 2:58:33 PM
```
Go to http://spark-packages.org/staging?id=<xxxx> to check status of your package. If your paackage is published successful, the state is changed to `valid`.

## Using package which is published

To include this package in your Spark Applications, please refer to `How to [+]` section in your spark package repo.

