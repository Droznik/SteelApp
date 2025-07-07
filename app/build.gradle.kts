import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.gsm.googleServices)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.compose.compiler)
}


android {
    namespace = "com.mdrapp.de"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.mdrapp.de"
        minSdk = 24
        targetSdk = 34

        versionCode = 8
        versionName = "1.0.6"

        setProperty("archivesBaseName", "mdr-v${versionName}_${versionCode}")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            gradle.projectsEvaluated {
                tasks.withType<JavaCompile> {
                    val compilerArgs = options.compilerArgs
                    compilerArgs.add("-Xlint:deprecation")
                }
            }
        }
    }

    signingConfigs {
        create("release") {

        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            versionNameSuffix = "-R"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            versionNameSuffix = "-D"
        }
    }

    flavorDimensions.add("env")
    productFlavors {
        create("dev") {
            dimension = "env"
            buildConfigField("String", "API_BASE_URL", "\"https://mdr-test.ado.systems/api/mdasdrapp/\"")
            buildConfigField("String", "CBS_API_BASE_URL", "\"https://stage.company.bike/api/saadadms2/v1/\"")
        }
        create("live") {
            dimension = "env"
            buildConfigField("String", "API_BASE_URL", "\"https://mdr-test.ado.systems/api/mdradadapp/\"")
            buildConfigField("String", "CBS_API_BASE_URL", "\"https://stage.company.bike/api/saasdams2/v1/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    hilt {
        enableAggregatingTask = true
    }
}

composeCompiler {
    enableStrongSkippingMode = true
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    implementation(libs.google.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.google.maps.compose)
    implementation(libs.maps.compose.utils)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.navigation.compose)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)

    implementation(libs.datastore.preferences)

    implementation(libs.coil.compose)

    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)

    implementation(libs.zoomable)
    implementation(libs.zxing.android.embedded)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
}