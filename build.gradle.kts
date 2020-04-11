buildscript {
    val kotlinVersion = meow.AppConfig.Versions.KOTLIN

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.6.2")
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${meow.AppConfig.Versions.NAVIGATION}")
    }
}

allprojects {

    tasks.withType(Javadoc::class) {
        (options as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
        options.encoding = "UTF-8"
    }
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
    delete(File("buildSrc\\build"))
}