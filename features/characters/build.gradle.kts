import app.web.drjackycv.buildsrc.Depends

plugins {
    id("common-android-lib")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Depends.Versions.androidCompileSdkVersion

    defaultConfig {
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        minSdk = Depends.Versions.minSdkVersion
        targetSdk = Depends.Versions.targetSdkVersion
        testInstrumentationRunner =
            Depends.Versions.testInstrumentationRunner
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        named("debug") { }
        named("release") {
            isMinifyEnabled = true
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
}

dependencies {
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.kotlin)
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.android_core_ktx)
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.fragment_ktx)
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.navigation_fragment_ktx)
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.paging_runtime_ktx)
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.paging_rx)
    //dependency injection
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.hilt_android)
    kapt(app.web.drjackycv.buildsrc.Depends.Libraries.hilt_android_compiler)
    kapt(app.web.drjackycv.buildsrc.Depends.Libraries.hilt_compiler)
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.java_inject)
    //other
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.material)
    //ui
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.glide)
    kapt(app.web.drjackycv.buildsrc.Depends.Libraries.glide_compiler)
    implementation(app.web.drjackycv.buildsrc.Depends.Libraries.lottie)
    //test
    testImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.junit)
    testImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.mockito_core)
    testImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.mockito_inline)
    testImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.mockito_kotlin)
    testImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.mockk)
    testImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.coroutines_test)
    testImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.arch_core_testing)
    androidTestImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.test_ext_junit)
    androidTestImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.test_runner)
    androidTestImplementation(app.web.drjackycv.buildsrc.Depends.Libraries.espresso_core)

    implementation(project(app.web.drjackycv.buildsrc.Depends.Core.navigation))
    implementation(project(app.web.drjackycv.buildsrc.Depends.Core.designSystem))
    implementation(project(app.web.drjackycv.buildsrc.Depends.Common.models))
}