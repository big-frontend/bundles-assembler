package com.jamesfchen.b

import org.gradle.api.Project

class AppModulePlugin extends AndroidPlugin {
    @Override
    String mainPlugin() {
        return 'com.android.application'
    }

    @Override
    void addPlugins(Project project) {
        super.addPlugins(project)
        if (routerPlugin) project.plugins.apply(routerPlugin)
        if (lifecycleVersion) {
            project.plugins.apply('io.github.jamesfchen.lifecycle-plugin')
        }
        if (navigationVersion) {
            project.plugins.apply('androidx.navigation.safeargs')
//        project.plugins.apply('androidx.navigation.safeargs.kotlin')
        }
    }

    @Override
    void onApply(Project project) {
        super.onApply(project)
        project.android {
            defaultConfig {
//        multiDexEnabled = true//support android 20 or lower
                applicationId project.rootProject.applicationId
            }
            signingConfigs {
                debugSigningConfig {
                    (keyAlias, keyPassword, storePassword) = [project.rootProject.keyAlias, project.rootProject.keyPassword, project.rootProject.storePassword]
                    storeFile project.file("$project.rootDir/$project.rootProject.storeFilePath")
                    v1SigningEnabled true
                    v2SigningEnabled true

                }
                releaseSigningConfig {
                    (keyAlias, keyPassword, storePassword) = [project.rootProject.keyAlias, project.rootProject.keyPassword, project.rootProject.storePassword]
                    storeFile project.file("$project.rootDir/$project.rootProject.storeFilePath")
                    v1SigningEnabled true
                    v2SigningEnabled true
                }

            }
            buildTypes {
                release {
                    signingConfig signingConfigs.releaseSigningConfig
                }
                debug {
                    signingConfig signingConfigs.debugSigningConfig
                }
            }
        }
    }
}