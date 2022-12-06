import app.web.drjackycv.buildsrc.Depends

plugins {
    id("common-android-lib")
    id("com.apollographql.apollo3") version 3.7.2
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

apollo {
    packageName.set("app.web.drjackycv.common.models")
    introspection {
        endpointUrl.set(Depends.Environments.debugBaseUrl)
        //schemaFile.set(file("src/main/graphql/app/web/drjackycv/schema.json"))
    }
    generateKotlinModels.set(true)
}

dependencies {
    implementation(Depends.Libraries.kotlin)
    implementation(Depends.Libraries.android_core_ktx)
    implementation(Depends.Libraries.paging_runtime_ktx)
    implementation(Depends.Libraries.paging_rx)
    //network
    implementation(Depends.Libraries.apollo_graphql_runtime)
    //test
    testImplementation(Depends.Libraries.junit)
    testImplementation(Depends.Libraries.mockito_core)
    testImplementation(Depends.Libraries.mockito_inline)
    testImplementation(Depends.Libraries.mockito_kotlin)
    testImplementation(Depends.Libraries.mockk)
    testImplementation(Depends.Libraries.coroutines_test)
    testImplementation(Depends.Libraries.arch_core_testing)
    androidTestImplementation(Depends.Libraries.test_ext_junit)
    androidTestImplementation(Depends.Libraries.test_runner)
    androidTestImplementation(Depends.Libraries.espresso_core)

    implementation(project(Depends.Core.network))
}

// https://github.com/apollographql/apollo-kotlin/issues/2287#issuecomment-1092785201
/*tasks.register("downloadSchema", com.apollographql.apollo3.gradle.internal.ApolloDownloadSchemaTask::class.java) {
    endpoint.set(Depends.Environments.debugBaseUrl)
    schema.set("common/models/src/main/graphql/app/web/drjackycv/schema.graphqls")
}*/
