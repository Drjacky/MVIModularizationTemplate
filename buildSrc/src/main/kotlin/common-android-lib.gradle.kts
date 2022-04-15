import app.web.drjackycv.buildsrc.Depends

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    compileSdk = Depends.Versions.androidCompileSdkVersion

    defaultConfig {
        minSdk = Depends.Versions.minSdkVersion
        targetSdk = Depends.Versions.targetSdkVersion
        testInstrumentationRunner =
            Depends.Versions.testInstrumentationRunner
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }
    }

    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/LGPL2.1",
                "META-INF/AL2.0",
                "META-INF/*.kotlin_module",
                "META-INF/rxjava.properties",
                "META-INF/proguard/androidx-annotations.pro"
            )
        )
    }
}

dependencies {
    implementation(Depends.Libraries.kotlin)
    implementation(Depends.Libraries.kotlin_reflect)
    implementation(Depends.Libraries.timber)
    implementation(Depends.Libraries.arrow)
    testImplementation(Depends.Libraries.junit)
}
