package com.github.ngyewch.dart.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.slf4j.Logger
import org.apache.tools.ant.taskdefs.condition.Os

class ExecuteWithDartVmTask extends DefaultTask {

	@TaskAction
	def run() {
        String dartExecutable = "${project.dart.dartSdkBin}dart"
		project.logger.lifecycle("Deploying dart files on DartVM relative to ${project.dart.sourceDirectory}")
        project.dart.executableDartFiles.each() {
            project.logger.lifecycle("Deploying file \${it}")
            project.exec {
                workingDir = project.dart.sourceDirectory
                if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                    commandLine "cmd", "/c", dartExecutable, "${it}"
                } else {
                    executable = dartExecutable
                    args = [it]
                    args.addAll(project.dart.commandLineParameters)
                    if (project.checkedMode) {
                        args.add("--checked")
                    }
                }
            }
        }
	}

}
