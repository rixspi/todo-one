
import java.io.FileInputStream
import java.time.LocalDate
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    kotlin("plugin.serialization")
    kotlin("plugin.compose")
    id("com.google.devtools.ksp")

    // id("com.google.gms.google-services")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "life.catchyour"
    compileSdk = 34

    defaultConfig {
        applicationId = "life.catchyour.todo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1_${LocalDate.now()}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = file("../debug.keystore")
            storePassword = "android"
        }
        create("release") {
            with(file("../signing.properties")) {
                if (canRead()) {
                    val properties = Properties()
                    properties.load(FileInputStream(this))

                    storeFile = rootProject.file(properties.getProperty("keystorePath"))
                    storePassword = properties.getProperty("keystorePassword")
                    keyAlias = properties.getProperty("keyAlias")
                    keyPassword = properties.getProperty("keyPassword")
                } else {
                    println("Unable to read signing.properties")
                }
            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            ndk.debugSymbolLevel = "FULL"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            versionNameSuffix = "_debug"
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-Xcontext-receivers",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
        )

        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=" +
                    "${project.projectDir}/compose_compiler_config.conf"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            pickFirsts.add("**.proto")
        }
    }
}

dependencies {
    implementation(AndroidX.exifInterface)
    implementation(project(":shared"))
    coreLibraryDesugaring(Android.tools.desugarJdkLibs)

    implementation(AndroidX.core.ktx)
    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.activity.compose)
    implementation(platform(AndroidX.compose.bom))
    implementation(AndroidX.compose.ui)
    implementation(AndroidX.compose.ui.graphics)
    implementation(AndroidX.compose.ui.toolingPreview)
    implementation(AndroidX.compose.material3)

    implementation(AndroidX.compose.material.icons.core)
    implementation(AndroidX.compose.material.icons.extended)
    implementation(AndroidX.compose.material3)

    implementation(Libs.android_image_cropper)

    implementation(platform(Firebase.bom))
    implementation(Firebase.analytics)
    implementation(Firebase.crashlytics)

    implementation(Libs.imageCompressor)

    implementation(Libs.resaca)
    implementation(Libs.resacahilt)

    implementation(AndroidX.core.splashscreen)

    implementation(Google.Accompanist.permissions)

    implementation(AndroidX.dataStore)
    implementation(AndroidX.dataStore.preferences)
    implementation(AndroidX.dataStore.preferences.core)

    implementation(Google.android.maps.compose)
    implementation(Libs.maps_compose_utils)
    implementation(Google.android.maps.places)
    implementation(Google.android.maps.ktx)
    implementation(Google.android.maps.places.ktx)
    implementation(Google.android.maps.utils.ktx)
    implementation(Google.android.maps.utils)

    implementation(Libs.googleid)
    implementation(AndroidX.credentials)
    // optional - needed for credentials support from play services, for devices running
    // Android 13 and below.
    implementation(AndroidX.credentials.playServicesAuth)


    implementation(KotlinX.serialization.core)
    implementation(KotlinX.serialization.json)
    implementation(KotlinX.serialization.properties)
    implementation(KotlinX.collections.immutable)

    implementation(Ktor.client.android)
    implementation(Ktor.client.core)
    implementation(Ktor.plugins.serialization.kotlinx.json)
    implementation(Ktor.client.contentNegotiation)
    implementation(Ktor.client.json)
    implementation(Ktor.client.logging)
    implementation(Ktor.client.auth)

    implementation(platform("io.insert-koin:koin-bom:_"))
    implementation(Koin.core)
    implementation(Koin.android)

    implementation(COIL.base)
    implementation(COIL.compose)

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")
    detektPlugins("io.nlopez.compose.rules:detekt:_")

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)
    androidTestImplementation(platform(AndroidX.compose.bom))
    androidTestImplementation(AndroidX.compose.ui.testJunit4)
    debugImplementation(AndroidX.compose.ui.tooling)
    debugImplementation(AndroidX.compose.ui.testManifest)
}

secrets {
    propertiesFileName = "secrets.properties"
}

detekt {
    config.setFrom(file("../config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}