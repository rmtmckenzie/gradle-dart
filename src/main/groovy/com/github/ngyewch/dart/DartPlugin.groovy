package com.github.ngyewch.dart

import com.github.ngyewch.dart.task.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class DartPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
        project.extensions.create('dart', DartPluginExtension)
        project.dart.initDefaultValues(project)

        project.task('pubGet', type: DartPubGetTask, group: 'Build', description: 'get all dart dependencies listed in the projects pubspec.yaml file. Directory of pubspec.yaml can be defined by property \'pubspecDirectory\'.')
        project.task('pubUpgrade', type: DartPubUpgradeTask, group: 'Build', description: 'upgrade project with latest versions of all dart dependencies listed in the projects pubspec.yaml file. Directory of pubspec.yaml can be defined by property \'pubspecDirectory\'.')
        project.task('webDevBuild', type: DartWebDevBuildTask, group: 'Build', description: 'build project.')
        project.task('webDevServe', type: DartWebDevServeTask, group: 'Build', description: 'start a development server for your Webapp.')
        project.task('pubPublish', type: DartPubPublishTask, group: 'Other', description: 'project is published via pub.')
        project.task('testDart', type: TestDartTask, group: 'Other', description: 'executes the tests in the \'testDirectory\'.')
        project.task('analyseDart', type: AnalyseDartTask, group: 'Other', description: 'analyses the files in the \'sourceDirectory\'.')
        project.task('executeWithDartVm', type: ExecuteWithDartVmTask, group: 'Other', description: 'executes the files defined by property \'executableDartFiles\' (Set) on the local dart vm.')
	}
}
