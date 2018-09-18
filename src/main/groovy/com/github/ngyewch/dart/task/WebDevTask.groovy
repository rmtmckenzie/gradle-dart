package com.github.ngyewch.dart.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class DartWebDevOutputAbstractTask extends DefaultTask {

    DartWebDevOutputAbstractTask() {
        project.afterEvaluate {
            inputs.dir project.dart.pubspecDirectory

            String outPath = project.dart.buildOutputDirectory

            for(String commandlineParam in project.dart.commandLineParameters) {
                if (commandlineParam.contains("--output=")) {
                    outPath = commandlineParam.substring(9)
                } else if (commandlineParam.contains("-o ")) {
                    outPath = commandlineParam.substring(3)
                }
            }

            outputs.dir outPath
        }
    }

    void executeWebDevCommand(String command) {
        String pubspecDirectory = project.dart.pubspecDirectory
        String webdevExecutable = "${project.dart.dartCacheBin}pub"

        List<String> commandArgs = Os.isFamily(Os.FAMILY_WINDOWS) ? ['cmd', '/c', webdevExecutable, command] : [command]
        commandArgs.addAll(project.dart.commandLineParameters)
        if (!commandArgs.collect{item -> item.contains("--output")}.any()) {
            commandArgs.add("--output=$project.dart.buildOutputDirectory")
        }

        project.exec {
            workingDir = pubspecDirectory
            if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                commandLine commandArgs
            } else {
                executable = webdevExecutable
                args = commandArgs
            }
        }
    }

}

class DartWebDevBuildTask extends DartWebDevOutputAbstractTask {

    @TaskAction
    def run() {
        project.logger.lifecycle("Building with WebDev")
        executeWebDevCommand('build')
    }

}


class DartWebDevServeTask extends DartWebDevOutputAbstractTask {

    @TaskAction
    def run() {
        project.logger.lifecycle("Serving with WebDev")
        executeWebDevCommand('serve')
    }

}
