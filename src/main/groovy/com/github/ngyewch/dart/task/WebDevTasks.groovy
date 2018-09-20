package com.github.ngyewch.dart.task

import com.github.ngyewch.dart.DefaultValueDartPluginExtension
import org.gradle.api.tasks.TaskAction

abstract class OutputtingAbstractTask extends AbstractPubTask {

    protected void setInputsAndOutputs(DefaultValueDartPluginExtension conf) {
        // this should be done within
        inputs.files project.fileTree(conf.pubspecDirectory).matching {
            exclude conf.buildIgnoreFiles
        }

        String outPath = conf.buildOutputDirectory

        for (String commandlineParam in conf.commandLineParameters) {
            if (commandlineParam.contains("--output=") || commandlineParam.contains("--output ")) {
                outPath = commandlineParam.substring(9)
                break
            } else if (commandlineParam.contains("-o ")) {
                outPath = commandlineParam.substring(3)
                break
            }
        }

        if (outPath.startsWith("web:")) {
            outPath = outPath.substring(4)
        }

        outputs.dir outPath
    }


    @Override
    void executePubCommand(String command, boolean global, DefaultValueDartPluginExtension conf) {
        List<String> pubArgs = getArgs(command, global, conf)

        if (!pubArgs.collect { item -> item.contains("--output") }.any()) {
            pubArgs << ("--output=web:$conf.buildOutputDirectory").toString()
        }

        executePubCommand(conf, pubArgs)
    }

}

class DartWebDevBuildTask extends OutputtingAbstractTask {

    DartWebDevBuildTask() {
        project.afterEvaluate {
            setInputsAndOutputs(project.dart.webDevBuild as DefaultValueDartPluginExtension)
        }
    }

    @TaskAction
    def run() {
        project.logger.lifecycle("Building with WebDev")
        executePubCommand('webdev build', true, project.dart.webDevServe as DefaultValueDartPluginExtension)
    }

}


class DartWebDevServeTask extends OutputtingAbstractTask {

    DartWebDevServeTask() {
        project.afterEvaluate {
            setInputsAndOutputs(project.dart.webDevServe as DefaultValueDartPluginExtension)
        }
    }

    @TaskAction
    def run() {
        project.logger.lifecycle("Serving with WebDev")
        executePubCommand('webdev serve', true, project.dart.webDevServe as DefaultValueDartPluginExtension)
    }

}
