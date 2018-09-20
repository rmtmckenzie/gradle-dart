package com.github.ngyewch.dart.task

import com.github.ngyewch.dart.DefaultValueDartPluginExtension
import org.gradle.api.tasks.TaskAction

class DartPubGetTask extends AbstractPubTask {

    DartPubGetTask() {
        project.afterEvaluate {
            inputs.file "${project.dart.pubGet.pubspecDirectory}/pubspec.yaml"
            outputs.file "${project.dart.pubGet.pubspecDirectory}/pubspec.lock"
            outputs.dir "${project.dart.pubGet.pubspecDirectory}/.pub"
        }
    }

    @TaskAction
    def run() {
        project.logger.lifecycle("Installing dependencies defined in ${project.dart.pubGet.pubspecDirectory}/pubspec.yaml")
        executePubCommand("get", project.dart.pubGet as DefaultValueDartPluginExtension)
    }

}

class DartPubUpgradeTask extends AbstractPubTask {

    @TaskAction
    def run() {
        project.logger.lifecycle("Upgrade project with latest versions of dependencies defined in ${project.dart.pubUpgrade.pubspecDirectory}/pubspec.yaml")
        executePubCommand("upgrade", project.dart.pubUpgrade as DefaultValueDartPluginExtension)
    }

}


class DartPubPublishTask extends AbstractPubTask {

    @TaskAction
    def run() {
        project.logger.lifecycle("Publishing project")
        executePubCommand("publish", project.dart.pubPublish as DefaultValueDartPluginExtension)
    }

}