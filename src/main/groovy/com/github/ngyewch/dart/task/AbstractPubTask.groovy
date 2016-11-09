package com.github.ngyewch.dart.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask

abstract class AbstractPubTask extends DefaultTask {

    void executePubCommand(String command) {
        String pubspecDirectory = project.dart.pubspecDirectory
        String pubExecutable = "${project.dart.dartSdkBin}pub"
        project.exec {
            workingDir = pubspecDirectory
            if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                commandLine 'cmd', '/c', pubExecutable, command
            } else {
                executable = pubExecutable
                args = [command]
                args.addAll(project.dart.commandLineParameters)
            }
        }
    }
}
