plugins {
//    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.7.21")
    implementation("com.android.tools.build:gradle:7.3.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0-RC2")
    implementation("com.squareup:javapoet:1.13.0")
}