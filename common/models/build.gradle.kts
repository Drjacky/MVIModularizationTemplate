import app.web.drjackycv.buildsrc.Depends

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.apollographql.apollo3") version app.web.drjackycv.buildsrc.Depends.Versions.apolloGraphqlVersion
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
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    sourceSets {
        val test by getting

        map {
            it.java.srcDir("src/${it.name}/kotlin")
            //test.java.srcDir("${project(":domain").projectDir}/src/test/java")
        }
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

apollo {
    packageName.set("app.web.drjackycv.common.models")
    introspection {
        endpointUrl.set(Depends.Environments.debugBaseUrl)
        schemaFile.set(file("src/main/graphql/app/web/drjackycv/schema.json"))
    }
    generateKotlinModels.set(true)
}

dependencies {
    implementation(Depends.Libraries.kotlin)
    implementation(Depends.Libraries.android_core_ktx)
    implementation(Depends.Libraries.paging_runtime_ktx)
    implementation(Depends.Libraries.paging_rx)
    implementation(Depends.Libraries.multidex)
    //network
    implementation(Depends.Libraries.retrofit)
    implementation(Depends.Libraries.retrofit_adapter_rx)
    implementation(Depends.Libraries.logging_interceptor)
    debugImplementation(Depends.Libraries.chucker)
    releaseImplementation(Depends.Libraries.chucker_no_op)
    implementation(Depends.Libraries.apollo_graphql_runtime)
    implementation(Depends.Libraries.arrow)
    //other
    implementation(Depends.Libraries.material)
    //test
    testImplementation(Depends.Libraries.junit)
    testImplementation(Depends.Libraries.test_ext_junit)
    testImplementation(Depends.Libraries.mockito_core)
    testImplementation(Depends.Libraries.mockito_inline)
    testImplementation(Depends.Libraries.mockito_kotlin)
    testImplementation(Depends.Libraries.mockk)
    testImplementation(Depends.Libraries.coroutines_test)
    testImplementation(Depends.Libraries.arch_core_testing)
}

// https://github.com/apollographql/apollo-kotlin/issues/2287#issuecomment-1092785201
/*tasks.register("downloadSchema", com.apollographql.apollo3.gradle.internal.ApolloDownloadSchemaTask::class.java) {
    endpoint.set(Depends.Environments.debugBaseUrl)
    schema.set("common/models/src/main/graphql/app/web/drjackycv/schema.graphqls")
}*/
