import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ArtSeum"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.kotlinx.coroutines.android)

            // Ktor
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            api(projects.core.common)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)

            // Konnection check
            implementation(libs.konnection)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}