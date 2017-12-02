package com.github.ngyewch.dart.task

import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

class DartPubGetTask extends AbstractPubTask {

    DartPubGetTask() {
        project.afterEvaluate {
            inputs.file "${project.dart.pubspecDirectory}/pubspec.yaml"
            outputs.file "${project.dart.pubspecDirectory}/pubspec.lock"
            outputs.dir "${project.dart.pubspecDirectory}/.pub"
        }
    }

    @TaskAction
    def run() {
        project.logger.lifecycle("Installing dependencies defined in ${project.dart.pubspecDirectory}/pubspec.yaml")
        executePubCommand("get")
    }

}

class DartPubUpgradeTask extends SourceTask {

    @TaskAction
    def run() {
        project.logger.lifecycle("Upgrade project with latest versions of dependencies defined in ${project.dart.pubspecDirectory}/pubspec.yaml")
        executePubCommand("upgrade")
    }

}

class DartPubServeTask extends AbstractPubTask {

    @TaskAction
    def run() {
        project.logger.lifecycle("Start a development server for your Webapp")
        executePubCommand("serve")
    }

}

class DartPubPublishTask extends AbstractPubTask {

    @TaskAction
    def run() {
        project.logger.lifecycle("Publishing project")
        executePubCommand("publish")
    }

}