package com.github.ngyewch.dart

import com.github.ngyewch.dart.task.*
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

interface DartPluginTestConstants {
    static final String TEST_DART_SDK_HOME = "TEST_DART_SDK_HOME"
    static final String TEST_DART_SDK_BIN = "TEST_DART_SDK_BIN"
    static final Set<String> TEST_EXECUTABLE_DART_FILES = ["Test.dart", "Test2.dart"]
    static final List<String> TEST_COMMANDLINE_PARAMETERS = ["-PARAM1", "-PARAM2"]
    static final String TEST_PUBSPEC_DIRECTORY = "TEST_PUBSPEC_DIRECTORY"
    static final String TEST_SOURCE_DIRECTORY = "TEST_SOURCE_DIRECTORY"
    static final String TEST_RELATIVE_SOURCE_DIRECTORY = "TEST_RELATIVE_SOURCE_DIRECTORY"
    static final String TEST_TEST_DIRECTORY = "TEST_TEST_DIRECTORY"
    static final String TEST_RELATIVE_TEST_DIRECTORY = "TEST_RELATIVE_TEST_DIRECTORY"
    static final String TEST_BUILD_OUTPUT_DIRECTORY = "TEST_BUILD_OUTPUT_DIRECTORY"
    static final Set<String> TEST_BUILD_IGNORE = ["ignore1.dart", "ignore2.dart"]
}

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
        project.tasks.analyzeDart instanceof AnalyzeDartTask
        project.tasks.testDart instanceof TestDartTask
        project.tasks.executeWithDartVm instanceof ExecuteWithDartVmTask
	}

    def "configurations should be added"() {
        expect:
        project.dart.pubGet instanceof PubGetExtension
        project.dart.pubUpgrade instanceof PubUpgradeExtension
        project.dart.webDevBuild instanceof WebDevBuildExtension
        project.dart.webDevServe instanceof WebDevServeExtension
        project.dart.pubPublish instanceof PubPublishExtension
        project.dart.analyzeDart instanceof AnalyzeDartExtension
        project.dart.testDart instanceof TestDartExtension
        project.dart.executeWithDartVm instanceof ExecuteWithDartVmExtension
    }
}

class DartPluginWithTaskConfigurationsTest extends Specification implements DartPluginTestConstants {

    Project project = ProjectBuilder.builder().build()

    def setup() {
        project.apply plugin: DartPlugin
    }

    def "setting default properties test"() {
        project.dart {
            dartSdkBin = TEST_DART_SDK_HOME
            commandLineParameters = TEST_COMMANDLINE_PARAMETERS
            pubspecDirectory = TEST_PUBSPEC_DIRECTORY
            sourceDirectory = TEST_SOURCE_DIRECTORY
            testDirectory = TEST_TEST_DIRECTORY
            buildOutputDirectory = TEST_BUILD_OUTPUT_DIRECTORY
            buildIgnore = TEST_BUILD_IGNORE
        }

        def confs = [
                project.dart.analyzeDart,
                project.dart.executeWithDartVm,
                project.dart.pubGet,
                project.dart.pubPublish,
                project.dart.pubUpgrade,
                project.dart.testDart,
                project.dart.webDevBuild,
                project.dart.webDevServe,
        ]
        for(DefaultValueDartPluginExtension conf in confs) {
            expect:
            conf.dartSdkBin == TEST_DART_SDK_BIN
            conf.pubExecutable == TEST_DART_SDK_BIN + "path"
            TEST_COMMANDLINE_PARAMETERS.equals(conf.commandLineParameters)
            conf.pubspecDirectory == TEST_PUBSPEC_DIRECTORY
            conf.sourceDirectory == TEST_SOURCE_DIRECTORY
            conf.testDirectory == TEST_TEST_DIRECTORY
            conf.buildOutputDirectory == TEST_BUILD_OUTPUT_DIRECTORY
            TEST_BUILD_IGNORE.equals(conf.buildIgnore)
        }
    }
}

class AnalyzeDartExtensionTest extends Specification {
    Project project = ProjectBuilder.builder().build()

    def setup() {
        project.apply plugin: DartPlugin
    }

    def "setting properties test"() {
        project.dart.analyzeDart.analyzePackagesFolders = true

        expect:
        project.dart.analyzeDart.analyzePackagesFolders == true
    }
}

class ExecuteWithDartVmExtensionTest extends Specification {
    Project project = ProjectBuilder.builder().build()

    static final Set<String> TEST_EXECUTE_WITH_DART_VM_FILES = ['one', 'two', 'three']

    def setup() {
        project.apply plugin: DartPlugin
    }

    def "setting properties test"() {
        project.dart.executeWithDartVm.files = TEST_EXECUTE_WITH_DART_VM_FILES

        expect:
        TEST_EXECUTE_WITH_DART_VM_FILES.equals(project.dart.executeWithDartVm.files)
    }
}


class TestDartExtensionTest extends Specification {
    Project project = ProjectBuilder.builder().build()

    def setup() {
        project.apply plugin: DartPlugin
    }

    def "setting properties test"() {
        project.dart.testDart.testPackagesFolders = true

        expect:
        project.dart.testDart.testPackagesFolders == true
    }
}



class DartPluginWithConfigurationTest extends Specification implements DartPluginTestConstants {

    Project project = ProjectBuilder.builder().build()



    def setup() {
        project.apply plugin: DartPlugin
    }

    def "defaults test"() {
        String projectDir = project.getProjectDir().toString()

        expect:
        project.dart.dartSdkBin == ''
        project.dart.commandLineParameters == []
        project.dart.pubspecDirectory == projectDir
        project.dart.sourceDirectory == "$projectDir/lib/"
        project.dart.testDirectory == "$projectDir/test"
        project.dart.buildOutputDirectory == "$projectDir/build/dart"
        project.dart.buildIgnore.equals(['build', '.dart_tool', '.idea'] as Set)
    }

    def "setting properties test"() {
        project.dart {
            dartSdkBin = TEST_DART_SDK_BIN
            commandLineParameters = TEST_COMMANDLINE_PARAMETERS
            pubspecDirectory = TEST_PUBSPEC_DIRECTORY
            sourceDirectory = TEST_SOURCE_DIRECTORY
            testDirectory = TEST_TEST_DIRECTORY
            buildOutputDirectory = TEST_BUILD_OUTPUT_DIRECTORY
            buildIgnore = TEST_BUILD_IGNORE
        }

        expect:
        project.dart.dartSdkBin == TEST_DART_SDK_BIN
        TEST_COMMANDLINE_PARAMETERS.equals(project.dart.commandLineParameters)
        project.dart.pubspecDirectory == TEST_PUBSPEC_DIRECTORY
        project.dart.sourceDirectory == TEST_SOURCE_DIRECTORY
        project.dart.testDirectory == TEST_TEST_DIRECTORY
        project.dart.buildOutputDirectory == TEST_BUILD_OUTPUT_DIRECTORY
        TEST_BUILD_IGNORE.equals(project.dart.buildIgnore)
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