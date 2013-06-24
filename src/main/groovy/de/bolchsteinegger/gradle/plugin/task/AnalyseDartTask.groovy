package de.bolchsteinegger.gradle.plugin.task

import groovy.io.FileType
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.slf4j.Logger

class AnalyseDartTask extends DefaultTask {

    @TaskAction
    def run() {
        String dartExecutable = "${project.dart.dartSdkBin}dartanalyzer"
        project.logger.lifecycle("Analysing files in \"${project.dart.sourceDirectory}\".")
        Integer fileCount = executeDartFilesInPath("${project.dart.sourceDirectory}", dartExecutable)
        project.logger.lifecycle("Analysed $fileCount files.")
    }

    Integer executeDartFilesInPath(String path, String dartExecutable) {
        Integer executedFileCount = 0;
        File files = new File(path)
        if (files.exists() && folderShallBeTested(files)) {
            files.eachFileMatch(FileType.FILES, ~/.*\.dart/) { file ->
                project.logger.lifecycle("Analysing file: ${file}")
                project.exec {
                    workingDir = project.dart.sourceDirectory
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

    Boolean folderShallBeTested(File folder) {

        return (!(folder.toString().endsWith("packages") && !project.dart.analysePackagesFolders) && (!folder.toString().endsWith("src")))
    }
}
