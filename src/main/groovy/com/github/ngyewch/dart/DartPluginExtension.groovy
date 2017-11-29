package com.github.ngyewch.dart

import org.gradle.api.Project

class DartPluginExtension {
	String dartSdkBin
    Set<String> executableDartFiles
    Boolean checkedMode
    Set<String> commandLineParameters
    String pubspecDirectory
    String sourceDirectory
    String testDirectory
    Boolean testPackagesFolders
    Boolean analysePackagesFolders
    String buildOutputDirectory

    Project project;

    void setDartSdkHome(String dartSdkHome) {
        this.dartSdkBin = combineStringsWithSlash(dartSdkHome, "bin/")
    }

    void setRelativeSourceDirectory(String relativeSourceDirectory) {
        String projectDir = project.getProjectDir().toString()

        relativeSourceDirectory = makeStringEndWithSlash(relativeSourceDirectory)
        this.sourceDirectory = combineStringsWithSlash(projectDir, relativeSourceDirectory)
    }

    void setRelativeTestDirectory(String relativeTestDirectory) {
        String projectDir = project.getProjectDir().toString()

        relativeTestDirectory = makeStringEndWithSlash(relativeTestDirectory)
        this.testDirectory = combineStringsWithSlash(projectDir, relativeTestDirectory)
    }

    void initDefaultValues(Project project) {
        this.project = project

        String projectDirectory = "${project.getProjectDir().toString()}"
        String defaultSourceDirectory = combineStringsWithSlash(projectDirectory, "lib/")
        String defaultTestDirectory = combineStringsWithSlash(projectDirectory, "test")
        String defaultBuildOutputDirectory = combineStringsWithSlash(projectDirectory, "build/dart")
        project.dart {
            dartSdkBin = ''
            executableDartFiles = new HashSet<String>()
            checkedMode = false
            commandLineParameters = new HashSet<String>()
            pubspecDirectory = projectDirectory
            sourceDirectory = defaultSourceDirectory
            testDirectory = defaultTestDirectory
            testPackagesFolders = false
            analysePackagesFolders = false
            buildOutputDirectory = defaultBuildOutputDirectory
        }
        if (System.getenv('DART_SDK') != null) {
            project.dart.dartSdkBin = combineStringsWithSlash("${System.getenv('DART_SDK')}", "bin/")
        }
    }

    private String combineStringsWithSlash(String firstString, String secondString) {
        if (!secondString.startsWith('/') && !firstString.endsWith('/')) {
            secondString = "/$secondString"
        } else if (secondString.startsWith('/') && firstString.endsWith('/')) {
            relativeTestDirectory = secondString.substring(0, secondString.length() - 1);
        }
        return "$firstString$secondString"
    }

    private String makeStringEndWithSlash(String relativeSourceDirectory) {
        if (!relativeSourceDirectory.endsWith('/')) {
            relativeSourceDirectory = "$relativeSourceDirectory/"
        }
        return relativeSourceDirectory
    }
}
