package org.davy.utils.plugin;

import org.gradle.api.Plugin
import org.gradle.api.Project

public class InjectPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def log = project.logger;
        println("========================");
        println("InjectPlugin!");
        println(project.version);
        println("========================");
        project.android.registerTransform(new OnlyCopyTransform(project))
        project.android.registerTransform(new InjectTransform(project))
    }
}