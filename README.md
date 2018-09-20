# Gradle Dart Plugin

Provides Gradle integration for Dart projects.

Forked from [ngyewch/gradle-dart](https://github.com/ngyewch/gradle-dart), which was
forked from [steineggerroland/gradle-dart](https://github.com/steineggerroland/gradle-dart)

## Example usage

    plugins {
        id 'com.github.rmtmckenzie.gradle-dart' version '1.1.0'
    }


## Available gradle tasks

- __pubGet__ (resolves dependencies with pub get)
- __pubUpgrade__ (upgrade project with latest versions of dependencies with pub upgrade)
- __pubPublish__ (publishes the project with pub publish)
- __webDevBuild__ (build project)
- __webDevServe__ (start a development server for your webapp)
- __analyzeDart__ (analyzes files in source folder)
- __testDart__ (executes tests in test folder)
- __executeWithDartVm__ (executes specified files)

## Available settings
- __dartSdkHome / dartSdkBin__
  - Default: empty string or 'DART_SDK' environment variable
  - Used for execution of dart commands. Set 'dartSdkHome' or 'dartSdkBin' if you don't have the dart sdk bin in your path variables.

- __commandLineParameters__
  - Default: empty list
  - Additional parameters for command execution.

- __pubspecDirectory__
  - Default: Project directory (Location of build.gradle)
  - Directory of 'pubspec.yaml' file.
 
- __sourceDirectory / relativeSourceDirectory__
  - Default: $ProjectDirectory$/lib/src (according to preferred project structure)
  - Needed for analyzing source files. The path can be set relative to the project directory.

- __testDirectory / relativeTestDirectory__
  - Default: $ProjectDirectory$/test (according to preferred project structure)
  - Needed for executing tests. The path can be set relative to the project directory.

- __buildOutputDirectory__
  - Default: build/dart
  - Directory of build output
  
- __buildIgnore__
  - Default: `['build', '.dart_tool', '.idea']`
  - Paths to ignore for build-related tasks (makes build faster)
  

## Available task settings
In addition to these parameters, it is possible to configure each individual task. The settings for each
task is in the corresponding named parameter for that task.

i.e.
```gradle
    dart {
        pubspecDirectory "a/directory"
        webDevBuild {
          pubSpecDirectory "a/different/directory"
        }
    }
```
Certain tasks also have additional settings:
- __analyzeDart.analyzePackagesFolders__
  - Default: false
  - Whether or not to analyze items in the packages folders
  
- __executeWithDartVm.files__
  - Default: []
  - The files to execute

- __testDart.testPackagesFolders__
  - Default: false
  - Wether or not to run test in the packages folders 