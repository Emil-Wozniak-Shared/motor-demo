import com.github.gradle.node.task.NodeTask

plugins {
    id("com.github.node-gradle.node") version "7.0.2"
}
repositories {
    mavenCentral()
    gradlePluginPortal()
}

tasks {
    create<NodeTask>("build") {
        setDependsOn(listOf("npmInstall"))
        script.set(file("./node_modules/.bin/react-scripts"))
        args.set(listOf("build"))
    }
    create<NodeTask>("run") {
        setDependsOn(listOf("npmInstall"))
        script.set(file("./node_modules/.bin/react-scripts"))
        args.set(listOf("start"))
    }
    create<Delete>("clean") {
        setDelete(listOf("node_modules", "build"))
    }
}
