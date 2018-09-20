package com.github.ngyewch.dart

import com.github.ngyewch.dart.task.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class DartPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('dart', DartPluginExtension)
        project.dart.initDefaultValues(project)

        project.dart.extensions.create('pubGet', PubGetExtension)
        project.dart.pubGet.initDefaultValues(project)

        project.dart.extensions.create('pubUpgrade', PubUpgradeExtension)
        project.dart.pubUpgrade.initDefaultValues(project)

        project.dart.extensions.create('webDevBuild', WebDevBuildExtension)
        project.dart.webDevBuild.initDefaultValues(project)

        project.dart.extensions.create('webDevServe', WebDevServeExtension)
        project.dart.webDevServe.initDefaultValues(project)

        project.dart.extensions.create('pubPublish', PubPublishExtension)
        project.dart.pubPublish.initDefaultValues(project)

        project.dart.extensions.create('testDart', TestDartExtension)
        project.dart.testDart.initDefaultValues(project)

        project.dart.extensions.create('analyzeDart', AnalyzeDartExtension)
        project.dart.analyzeDart.initDefaultValues(project)

        project.dart.extensions.create('executeWithDartVm', ExecuteWithDartVmExtension)
        project.dart.executeWithDartVm.initDefaultValues(project)

        project.task('pubGet', type: DartPubGetTask, group: 'Build', description: 'get all dart dependencies listed in the projects pubspec.yaml file. Directory of pubspec.yaml can be defined by property \'pubspecDirectory\'.')
        project.task('pubUpgrade', type: DartPubUpgradeTask, group: 'Build', description: 'upgrade project with latest versions of all dart dependencies listed in the projects pubspec.yaml file. Directory of pubspec.yaml can be defined by property \'pubspecDirectory\'.')
        project.task('webDevBuild', type: DartWebDevBuildTask, group: 'Build', description: 'build project.')
        project.task('webDevServe', type: DartWebDevServeTask, group: 'Build', description: 'start a development server for your Webapp.')
        project.task('pubPublish', type: DartPubPublishTask, group: 'Other', description: 'project is published via pub.')
        project.task('testDart', type: TestDartTask, group: 'Other', description: 'executes the tests in the \'testDirectory\'.')
        project.task('analyzeDart', type: AnalyzeDartTask, group: 'Other', description: 'analyzes the files in the \'sourceDirectory\'.')
        project.task('executeWithDartVm', type: ExecuteWithDartVmTask, group: 'Other', description: 'executes the files defined by property \'executableDartFiles\' (Set) on the local dart vm.')
    }
}
