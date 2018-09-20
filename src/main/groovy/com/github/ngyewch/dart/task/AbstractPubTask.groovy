package com.github.ngyewch.dart.task

import com.github.ngyewch.dart.DefaultValueDartPluginExtension
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask

abstract class AbstractPubTask extends DefaultTask {

    void executePubCommand(DefaultValueDartPluginExtension conf, List<String> runArgs) {
        String pubspecDirectory = conf.pubspecDirectory
        String runExecutable = Os.isFamily(Os.FAMILY_WINDOWS) ? 'cmd' : conf.pubExecutable

        project.exec {
            workingDir = pubspecDirectory
            executable = runExecutable
            args = runArgs
        }
    }

    List<String> getArgs(String command, boolean global, DefaultValueDartPluginExtension conf) {
        List<String> runArgs = []
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            runArgs << '/c' << conf.pubExecutable
        }

        if (global) {
            runArgs << 'global' << 'run'
        }

        runArgs << command

        runArgs.addAll(conf.commandLineParameters)

        return runArgs
    }

    void executePubCommand(String command, boolean global, DefaultValueDartPluginExtension conf) {
        executePubCommand(conf, getArgs(command, global, conf))
    }

    void executePubCommand(String command, DefaultValueDartPluginExtension conf) {
        executePubCommand(conf, getArgs(command, false, conf))
    }

}
