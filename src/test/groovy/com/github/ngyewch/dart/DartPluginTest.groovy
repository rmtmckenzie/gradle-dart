package com.github.ngyewch.dart

import com.github.ngyewch.dart.task.*
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class DartPluginWithoutConfigurationTest extends Specification {

	Project project = ProjectBuilder.builder().build()

    def setup() {
		project.apply plugin: DartPlugin
	}

	def "tasks should be added"() {
		expect:
        project.tasks.pubGet instanceof DartPubGetTask
        project.tasks.pubUpgrade instanceof DartPubUpgradeTask
        project.tasks.webDevBuild instanceof DartWebDevBuildTask
        project.tasks.webDevServe instanceof DartWebDevServeTask
        project.tasks.pubPublish instanceof DartPubPublishTask
        project.tasks.analyseDart instanceof AnalyseDartTask
        project.tasks.testDart instanceof TestDartTask
        project.tasks.executeWithDartVm instanceof ExecuteWithDartVmTask
	}
}

class DartPluginWithConfigurationTest extends Specification {

    Project project = ProjectBuilder.builder().build()

    final String TEST_DART_SDK_HOME = "TEST_DART_SDK_HOME"
    final String TEST_DART_SDK_BIN = "TEST_DART_SDK_BIN"
    final Set<String> TEST_EXECUTABLE_DART_FILES = new HashSet<String>(Arrays.asList({"Test.dart"; "Test2.dart"}))
    final Boolean TEST_CHECK_MODE = true
    final Set<String> TEST_COMMANDLINE_PARAMETERS = new HashSet<String>(Arrays.asList({"-PARAM1"; "-PARAM2"}))
    final String TEST_PUBSPEC_DIRECTORY = "TEST_PUBSPEC_DIRECTORY"
    final String TEST_SOURCE_DIRECTORY = "TEST_SOURCE_DIRECTORY"
    final String TEST_RELATIVE_SOURCE_DIRECTORY = "TEST_RELATIVE_SOURCE_DIRECTORY"
    final String TEST_TEST_DIRECTORY = "TEST_TEST_DIRECTORY"
    final String TEST_RELATIVE_TEST_DIRECTORY = "TEST_RELATIVE_TEST_DIRECTORY"
    final String TEST_BUILD_OUTPUT_DIRECTORY = "TEST_BUILD_OUTPUT_DIRECTORY"

    def setup() {
        project.apply plugin: DartPlugin
        project.dart.initDefaultValues(project)
    }

    def "setting properties test"() {
        project.dart {
            dartSdkBin = TEST_DART_SDK_BIN
            executableDartFiles = TEST_EXECUTABLE_DART_FILES
            checkedMode = TEST_CHECK_MODE
            commandLineParameters = TEST_COMMANDLINE_PARAMETERS
            pubspecDirectory = TEST_PUBSPEC_DIRECTORY
            sourceDirectory = TEST_SOURCE_DIRECTORY
            testDirectory = TEST_TEST_DIRECTORY
            buildOutputDirectory = TEST_BUILD_OUTPUT_DIRECTORY
        }

        expect:
        project.dart.dartSdkBin == TEST_DART_SDK_BIN
        project.dart.checkedMode == TEST_CHECK_MODE
        project.dart.pubspecDirectory == TEST_PUBSPEC_DIRECTORY
        project.dart.sourceDirectory == TEST_SOURCE_DIRECTORY
        project.dart.testDirectory == TEST_TEST_DIRECTORY
        project.dart.buildOutputDirectory == TEST_BUILD_OUTPUT_DIRECTORY
        TEST_EXECUTABLE_DART_FILES.equals(project.dart.executableDartFiles)
        TEST_COMMANDLINE_PARAMETERS.equals(project.dart.commandLineParameters)
    }

    def "property dart sdk home test"() {
        project.dart {
            dartSdkHome = TEST_DART_SDK_HOME
        }

        String projectHome = project.getProjectDir().toString()
        expect:
        project.dart.dartSdkBin == "$TEST_DART_SDK_HOME/bin/"
    }

    def "property relative test directory test"() {
        project.dart {
            relativeTestDirectory = TEST_RELATIVE_TEST_DIRECTORY
        }

        String projectHome = project.getProjectDir().toString()
        expect:
        project.dart.testDirectory == "$projectHome/$TEST_RELATIVE_TEST_DIRECTORY/"
    }

    def "property relative source directory test"() {
        project.dart {
            relativeSourceDirectory = TEST_RELATIVE_SOURCE_DIRECTORY
        }

        String projectHome = project.getProjectDir().toString()
        expect:
        project.dart.sourceDirectory == "$projectHome/$TEST_RELATIVE_SOURCE_DIRECTORY/"
    }
}