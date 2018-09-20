package com.github.ngyewch.dart.task

import groovy.io.FileType
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class AnalyzeDartTask extends DefaultTask {

    @TaskAction
    def run() {
        String dartExecutable = "${project.dart.analyzeDart.dartSdkBin}dartanalyzer"
        project.logger.lifecycle("Analyzing files in \"${project.dart.analyzeDart.sourceDirectory}\".")
        Integer fileCount = executeDartFilesInPath("${project.dart.analyzeDart.sourceDirectory}", dartExecutable)
        project.logger.lifecycle("Analyzed $fileCount files.")
    }

    Integer executeDartFilesInPath(String path, String dartExecutable) {
        Integer executedFileCount = 0;
        File files = new File(path)
        if (files.exists() && folderShallBeTested(files)) {
            files.eachFileMatch(FileType.FILES, ~/.*\.dart/) { file ->
                project.logger.lifecycle("Analyzing file: ${file}")
                project.exec {
                    workingDir = project.dart.analyzeDart.sourceDirectory
                    if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                        commandLine "cmd", "/c", dartExecutable, "${file}"
                        commandLine.addAll(project.dart.analyzeDart.commandLineParameters)
                    } else {
                        executable = dartExecutable
                        args = ["$file"]
                        args.addAll(project.dart.analyzeDart.commandLineParameters)
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
        boolean isPackageFolder = folder.toString().endsWith("packages")
        boolean packageFoldersShallBeAnalyzed = project.dart.analyzeDart.analyzePackagesFolders
        if (isPackageFolder && !packageFoldersShallBeAnalyzed) return false
        boolean isSourceFolder = folder.toString().endsWith("src")
        if (isSourceFolder) return false

        return true;
    }
}
