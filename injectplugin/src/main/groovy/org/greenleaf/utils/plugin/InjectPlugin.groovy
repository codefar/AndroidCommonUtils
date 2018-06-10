package org.greenleaf.utils.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class InjectPlugin implements Plugin<Project> {

    Logger mLogger = LoggerFactory.getLogger(Injecter.class)

    @Override
    void apply(Project project) {

        mLogger.info("========================")
        mLogger.info("InjectPlugin")
        mLogger.info("========================")

        def injectExtension = project.extensions.create('injectplugin', InjectExtension)
        println(injectExtension) //这里返回空，afterEvaluate才能获得值

        project.afterEvaluate {
            println(injectExtension)
        }

        project.android.registerTransform(new OnlyCopyTransform(project))
        project.android.registerTransform(new InjectTransform(project, injectExtension))

    }
}