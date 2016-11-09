package com.github.ngyewch.dart.task

import org.gradle.api.tasks.TaskAction

class DartPubGetTask extends AbstractPubTask {

    @TaskAction
    def run() {
        project.logger.lifecycle("Installing dependencies defined in ${project.dart.pubspecDirectory}/pubspec.yaml")
        executePubCommand("get")
    }

}

class DartPubBuildTask extends AbstractPubTask {

    @TaskAction
    def run() {
        project.logger.lifecycle("Building project")
        executePubCommand("build")
    }

}

class DartPubUpgradeTask extends AbstractPubTask {

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