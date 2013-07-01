package de.bolchsteinegger.gradle.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.slf4j.Logger
import org.apache.tools.ant.taskdefs.condition.Os
import groovy.io.FileType


class TestDartTask extends DefaultTask {

    @TaskAction
    def run() {
        String dartExecutable = "${project.dart.dartSdkBin}dart"
        project.logger.lifecycle("Executing tests in \"${project.dart.testDirectory}\".")
        Integer testsCount = executeDartFilesInPath("${project.dart.testDirectory}", dartExecutable)
        project.logger.lifecycle("Executed $testsCount tests.")
    }

    Integer executeDartFilesInPath(String path, String dartExecutable) {
        Integer executedFileCount = 0;
        File files = new File(path)
        if (files.exists() && folderShallBeTested(files)) {
            files.eachFileMatch(FileType.FILES, ~/.*\.dart/) { file ->
                project.logger.lifecycle("Running test file: ${file}")
                project.exec {
                    workingDir = project.dart.testDirectory
                    if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                        commandLine "cmd", "/c", dartExecutable, "${file}"
                        commandLine.addAll(project.dart.commandLineParameters)
                    } else {
                        executable = dartExecutable
                        args = ["$file"]
                        args.addAll(project.dart.commandLineParameters)
                    }
                }
                executedFileCount++
            }
            files.eachFile(FileType.DIRECTORIES) { subDirectory ->
                executedFileCount += executeDartFilesInPath(subDirectory.absolutePath, dartExecutable)
            }
        }
        return executedFileCount
    }

    Boolean fileShallBeTested(File file) {
        boolean isConfigurationFile = file.toString().endsWith('conf.dart');
        if (isConfigurationFile) return false;

        return true;
    }

    Boolean folderShallBeTested(File folder) {
        boolean isPackageFolder = folder.toString().endsWith("packages");
        boolean packageFoldersShallBeTested = project.dart.testPackagesFolders;
        if (isPackageFolder && !packageFoldersShallBeTested) return false;

        return true;
    }
}
