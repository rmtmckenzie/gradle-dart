package com.github.ngyewch.dart.task

import groovy.io.FileType
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class TestDartTask extends DefaultTask {

    @TaskAction
    def run() {
        String dartExecutable = "${project.dart.testDart.dartSdkBin}dart"
        project.logger.lifecycle("Executing tests in \"${project.dart.testDart.testDirectory}\".")
        Integer testsCount = executeDartFilesInPath("${project.dart.testDart.testDirectory}", dartExecutable)
        project.logger.lifecycle("Executed $testsCount tests.")
    }

    Integer executeDartFilesInPath(String path, String dartExecutable) {
        Integer executedFileCount = 0
        File files = new File(path)
        if (files.exists() && folderShallBeTested(files)) {
            files.eachFileMatch(FileType.FILES, ~/.*\.dart/) { file ->
                project.logger.lifecycle("Running test file: ${file}")

                List<String> runArgs = []
                String runExecutable
                if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                    runArgs << '/c' << dartExecutable
                    runExecutable = 'cmd'
                } else {
                    runExecutable = dartExecutable
                }

                runArgs << file.toString()
                runArgs.addAll(project.dart.testDart.commandLineParameters as List<String>)

                project.exec {
                    workingDir = project.dart.testDart.testDirectory
                    executable = runExecutable
                    args = runArgs
                }
                executedFileCount++
            }
            files.eachFile(FileType.DIRECTORIES) { subDirectory ->
                executedFileCount += executeDartFilesInPath(subDirectory.absolutePath, dartExecutable)
            }
        }
        return executedFileCount
    }

//    // Not currently being used.
//    Boolean fileShallBeTested(File file) {
//        boolean isConfigurationFile = file.toString().endsWith('conf.dart')
//        if (isConfigurationFile) return false
//
//        return true
//    }

    Boolean folderShallBeTested(File folder) {
        boolean isPackageFolder = folder.toString().endsWith("packages")
        boolean packageFoldersShallBeTested = project.dart.testDart.testPackagesFolders
        if (isPackageFolder && !packageFoldersShallBeTested) return false

        return true
    }
}
