import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "PdfCore"
            isStatic = true
        }
    }
    android {
       namespace = "com.nags.keepdoc.core.pdf"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.test)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
