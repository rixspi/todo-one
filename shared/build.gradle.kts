plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    id("kotlin-parcelize")
    id("app.cash.sqldelight")
    id("io.realm.kotlin") version "2.0.0"
}

kotlin {
    task("testClasses")

    androidTarget()
    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        commonMain.dependencies {
            // UI
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
            // /UI

            // INTERNET & CONNECTIVITY
            implementation(Ktor.client.core)
            // /INTERNET & CONNECTIVITY

            // COROUTINES & HELPERS
            implementation(KotlinX.coroutines.core)
            implementation(CashApp.SqlDelight.extensions.coroutines)
            // /COROUTINES & HELPERS

            implementation(KotlinX.datetime)

            implementation(Koin.core)

            implementation(KotlinX.serialization.core)
            implementation(KotlinX.serialization.json)


            implementation("io.realm.kotlin:library-base:1.16.0")
            // If using Device Sync
            // implementation("io.realm.kotlin:library-sync:1.16.0")

            val voyagerVersion = "1.1.0-beta02"

            // Multiplatform

            // Navigator
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

            // Screen Model
            implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")

            // BottomSheetNavigator
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")

            // TabNavigator
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")

            // Transitions
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

            // Android

            // Koin integration
            implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")

            val coil3 = "3.0.0-alpha06"
            implementation("io.coil-kt.coil3:coil-compose:$coil3")
            implementation("io.coil-kt.coil3:coil-compose-core:$coil3")
            implementation("io.coil-kt.coil3:coil-network-ktor:$coil3")
            implementation("io.coil-kt.coil3:coil:$coil3")

            implementation("sh.calvin.autolinktext:autolinktext:1.1.1")
        }

        commonTest.dependencies {
            implementation(Kotlin.test.common)
            implementation(Kotlin.test.annotationsCommon)
        }

        androidMain.dependencies {
            implementation(CashApp.SqlDelight.drivers.android)
            implementation(compose.components.uiToolingPreview)
            implementation(AndroidX.compose.ui.toolingPreview)
            implementation(AndroidX.compose.ui.tooling)
            implementation(Ktor.client.okHttp)
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(Kotlin.test.junit)
            }
        }

        iosMain.dependencies {
            implementation(CashApp.SqlDelight.drivers.native)

            implementation(Ktor.client.darwin)
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.components.uiToolingPreview)
                implementation(CashApp.SqlDelight.drivers.jdbcSqlite)
            }
        }
    }
}

android {
    namespace = "life.catchyour.dev.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    buildFeatures { compose = true }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {
    implementation("androidx.core:core:1.13.1")
}

sqldelight {
    databases {
        create("Database") {
            packageName = "life.catchyour.dev"
        }
    }
}