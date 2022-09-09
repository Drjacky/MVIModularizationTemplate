# MVIModularizationTemplate 🧞‍

![Language](https://img.shields.io/badge/Kotlin-1.7.10-blue) ![License](https://img.shields.io/github/license/Drjacky/MVIModularizationTemplate?logo=MIT) [![Actions Status](https://github.com/Drjacky/MVIModularizationTemplate/workflows/Pre%20Merge%20Checks/badge.svg)](https://github.com/Drjacky/MVIModularizationTemplate/actions) [![Build Status](https://github.com/Drjacky/MVIModularizationTemplate/workflows/Android%20CI/badge.svg)](https://github.com/Drjacky/MVIModularizationTemplate/actions) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Drjacky_MVIModularizationTemplate&metric=alert_status)](https://sonarcloud.io/dashboard?id=Drjacky_MVIModularizationTemplate)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FDrjacky%2FMVIModularizationTemplate.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2FDrjacky%2FMVIModularizationTemplate?ref=badge_shield)
<!-- [![Known Vulnerabilities](https://snyk.io/test/github/Drjacky/MVIModularizationTemplate/badge.svg)](https://snyk.io/test/github/Drjacky/MVIModularizationTemplate) Snyk doesn't support kotlin dsl -->
A simple Android template that lets you create an **Android** project quickly.

## How to use 👣

Just click
on [![Use this template](https://img.shields.io/badge/-Use%20this%20template-brightgreen)](https://github.com/Drjacky/MVIModularizationTemplate/generate)
button to create a new repo starting from this template.

## Features 🕹

- 100% Kotlin-only template
- Following MVI Architectural Design Pattern
- Template Project
- [Coroutines](https://developer.android.com/kotlin/coroutines) - A concurrency design pattern
  library
- [Flow](https://developer.android.com/kotlin/flow) - Built on top of coroutines and is stream of
  data that can be computed asynchronously
- Github Actions - CI
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection framework
- [Transition](https://developer.android.com/guide/navigation/navigation-animate-transitions) -
  Animation
- [Paging V3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) -
  Pagination
- [View Binding](https://developer.android.com/topic/libraries/view-binding) - View Binding
- [OkHttp3](https://github.com/square/okhttp) - Network interceptor
- [Retrofit](https://github.com/square/retrofit) - HTTP client
- [Glide](https://github.com/bumptech/glide) - Loading images
- [Timber](https://github.com/JakeWharton/timber) - Log
- [Gson](https://github.com/google/gson) - JSON library
- [Material Components](https://github.com/material-components/material-components-android) -
  Material Design
- [Lottie](https://airbnb.design/lottie/) - Vector animation library
- [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Alternative syntax to
  the Groovy DSL
- [Detekt](https://github.com/detekt/detekt) - Static code analysis for Kotlin
- [Gradle Doctor](https://github.com/runningcode/gradle-doctor) - Gradle build scan plugin
- [Navigation](https://developer.android.com/guide/navigation) - Navigate through the app
- [LeakCanary](https://square.github.io/leakcanary/) - Memory leak detection
- [Chucker](https://github.com/ChuckerTeam/chucker) - An HTTP inspector for Android & OkHTTP
- [StrictMode](https://developer.android.com/reference/android/os/StrictMode) - A developer tool
  which detects things you might be doing by accident

## CI 🏭

This template is using [**GitHub
Actions**](https://github.com/Drjacky/MVIModularizationTemplate/actions) as CI.

Available workflows listed as follows:

- [Validate Gradle Wrapper](.github/workflows/gradlew-validation.yml) - Checks the gradle wrapper
  has a valid checksum.
- [Pre Merge Checks](.github/workflows/pre-merge.yml) - Runs `build` task.
- [Android](.github/workflows/android.yml) - Runs `assembleDebug` task.

## Tasks 🔧

- Gradle Doctor: `dependencyUpdates` - Displays the dependency updates for the project.
- Detekt: `detektAll` - Run the static Kotlin code analysis for the whole project at once.
- The Dependency Analysis: `buildHealth` - Provides advice for managing dependencies and other
  applied plugins

## References 🧷

- [The Rick and Morty API](https://rickandmortyapi.com/)

## Contributing 🤝

Feel free to open an issue or submit a pull request for any bugs/improvements.

## Result 📺
<img src="https://raw.githubusercontent.com/Drjacky/MVVMTemplate/master/gif/demo.gif" width="350px" height="700px" />

## License ⚖️

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FDrjacky%2FMVIModularizationTemplate.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FDrjacky%2FMVIModularizationTemplate?ref=badge_large)