package com.github.ngyewch.dart.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ExecuteWithDartVmTask extends DefaultTask {

	@TaskAction
	def run() {
        String dartExecutable = "${project.dart.executeWithDartVm.dartSdkBin}dart"
		project.logger.lifecycle("Deploying dart files on DartVM relative to ${project.dart.executeWithDartVm.sourceDirectory}")
        project.dart.executeWithDartVm.files.each() {
            project.logger.lifecycle("Deploying file \${it}")

            List<String> runArgs = []
            String runExecutable
            if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                runArgs << '/c' << dartExecutable
                runExecutable = 'cmd'
            } else {
                runExecutable = dartExecutable
            }

            runArgs << it.toString()
            runArgs.addAll(project.dart.executeWithDartVm.commandLineParameters as List<String>)

            project.exec {
                workingDir = project.dart.executeWithDartVm.sourceDirectory
                executable = runExecutable
                args = runArgs
            }
        }
	}

}
