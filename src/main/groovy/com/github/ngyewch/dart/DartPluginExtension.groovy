package com.github.ngyewch.dart

import org.gradle.api.Project


class DartPluginExtension {
    String dartSdkBin
    List<String> commandLineParameters
    String pubspecDirectory
    String sourceDirectory
    String testDirectory
    String buildOutputDirectory
    Set<String> buildIgnore

    private Project project

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
        String defaultDefaultSourceDirectory = combineStringsWithSlash(projectDirectory, "lib/")
        String defaultDefaultTestDirectory = combineStringsWithSlash(projectDirectory, "test")
        String defaultDefaultBuildOutputDirectory = combineStringsWithSlash(projectDirectory, "build/dart")
        project.dart {
            dartSdkBin = ''
            commandLineParameters = []
            pubspecDirectory = projectDirectory
            sourceDirectory = defaultDefaultSourceDirectory
            testDirectory = defaultDefaultTestDirectory
            buildOutputDirectory = defaultDefaultBuildOutputDirectory
            buildIgnore = ['build', '.dart_tool', '.idea']
        }
        if (System.getenv('DART_SDK') != null) {
            dartSdkBin = combineStringsWithSlash("${System.getenv('DART_SDK')}", "bin/")
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

abstract class DefaultValueDartPluginExtension {

    private String dartSdkBin = null
    private List<String> commandLineParameters = null
    private String pubspecDirectory = null
    private String sourceDirectory = null
    private String testDirectory = null
    private String buildOutputDirectory = null
    private Set<String> buildIgnoreFiles = null

    String getDartSdkBin() {
        if (dartSdkBin == null) {
            return project.dart.dartSdkBin
        }
        return dartSdkBin
    }

    List<String> getCommandLineParameters() {
        if (commandLineParameters == null) {
            return project.dart.commandLineParameters
        }
        return commandLineParameters
    }

    String getPubspecDirectory() {
        if (pubspecDirectory == null) {
            return project.dart.pubspecDirectory
        }
        return pubspecDirectory
    }

    String getSourceDirectory() {
        if (sourceDirectory == null) {
            return project.dart.sourceDirectory
        }
    }

    String getTestDirectory() {
        if (testDirectory == null) {
            return project.dart.testDirectory
        }
    }

    String getBuildOutputDirectory() {
        if (buildOutputDirectory == null) {
            return project.dart.buildOutputDirectory
        }
        return buildOutputDirectory
    }

    String getBuildIgnoreFiles() {
        if (buildIgnoreFiles == null) {
            return project.dart.buildIgnore
        }
        return buildIgnoreFiles
    }

    String getPubExecutable() {
        return "${getDartSdkBin()}pub"
    }

    private Project project

    void initDefaultValues(Project project) {
        this.project = project
    }
}

class AnalyzeDartExtension extends DefaultValueDartPluginExtension {
    Boolean analyzePackagesFolders = false
}

class PubGetExtension extends DefaultValueDartPluginExtension {
    // Simply to be explicit, not really needed.
}

class PubPublishExtension extends DefaultValueDartPluginExtension {
    // Simply to be explicit, not really needed.
}

class PubUpgradeExtension extends DefaultValueDartPluginExtension {
    // Simply to be explicit, not really needed.
}

class ExecuteWithDartVmExtension extends DefaultValueDartPluginExtension {
    Set<String> files = new HashSet<>()
}

class TestDartExtension extends DefaultValueDartPluginExtension {
    // Simply to be explicit, not really needed.
    Boolean testPackagesFolders = false
}

class WebDevBuildExtension extends DefaultValueDartPluginExtension {
    // simply to be explicit, not really needed
}

class WebDevServeExtension extends DefaultValueDartPluginExtension {
    // simply to be explicit, not really needed
}