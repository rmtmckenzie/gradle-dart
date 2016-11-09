# Gradle Dart Plugin

Provides Gradle integration for Dart projects.


## Example usage

    plugins {
        id 'com.github.ngyewch.dart' version '0.3.0'
    }


## Available gradle tasks

- __pubGet__ (resolves dependencies with pub get)
- __pubUpgrade__ (upgrade project with latest versions of dependencies with pub upgrade)
- __pubBuild__ (build project)
- __pubServe__ (start a development server for your webapp)
- __pubPublish__ (publishes the project with pub publish)
- __analyseDart__ (alayses files in source folder)
- __testDart__ (executes tests in test folder)


## Available settings
- __dartSdkHome / dartSdkBin__
  - Default: empty string or 'DART_SDK' environment variable
  - Used for execution of dart commands. Set 'dartSdkHome' or 'dartSdkBin' if you don't have the dart sdk bin in your path variables.

- __commandLineParameters__
  - Default: empty set
  - Additional parameters for command execution.

- __pubspecDirectory__
  - Default: Project directory (Location of build.gradle)
  - Directory of 'pubspec.yaml' file.
 
- __sourceDirectory / relativeSourceDirectory__
  - Default: $ProjectDirectory$/lib/src (according to preferred project structure)
  - Needed for analysing source files. The path can be set relative to the project directory.

- __analysePackagesFolders__
  - Default: false
  - Define whether packages folder shall be analysed.

- __testDirectory / relativeTestDirectory__
  - Default: $ProjectDirectory$/test (according to preferred project structure)
  - Needed for executing tests. The path can be set relative to the project directory.

- __testPackagesFolders__
  - Default: false
  - Define whether packages folder shall be tested.
