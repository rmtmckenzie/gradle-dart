package com.github.ngyewch.dart.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


class DartPubBuildTask extends DefaultTask {
    @TaskAction
    def run() {
        project.logger.lifecycle("Building project")

        String pubspecDirectory = project.dart.pubspecDirectory
        String pubExecutable = "${project.dart.dartSdkBin}pub"

        List<String> commandArgs = Os.isFamily(Os.FAMILY_WINDOWS) ? ['cmd', '/c', pubExecutable, 'build'] : ['build']
        commandArgs.addAll(project.dart.commandLineParameters)
        if (!commandArgs.collect{item -> item.contains("--output")}.any()) {
            commandArgs.add("--output=$project.dart.buildOutputDirectory")
        }

        project.exec {
            workingDir = pubspecDirectory
            if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                commandLine commandArgs
            } else {
                executable = pubExecutable
                args = commandArgs
            }
        }
    }

}
