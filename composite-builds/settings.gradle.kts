//pluginManagement {
//    repositories {
//        maven {
//            url =uri("./local-repo")
//        }
//        gradlePluginPortal()
//    }
//    plugins {
//        id("io.github.jamesfchen.lifecycle-plugin")  version "1.0.0"
//        id("io.github.jamesfchen.ibc-plugin")  version "1.0.0"
//    }
//}
include(
    ":base-plugin", ":base-plugin-ktx",
    ":lifecycle:lifecycle-api", ":lifecycle:lifecycle-plugin",
    ":ibc:ibc-api", ":ibc:ibc-compiler", ":ibc:ibc-plugin", ":ibc:ibc-annotations",
)
include(":module-publisher-plugin")
include(":pg-annotations")
include(":bundles-assembler-plugin")
include(":replugin:replugin-plugin")
include(":replugin:replugin-host-api")
include(":replugin:replugin-parasite-api")
