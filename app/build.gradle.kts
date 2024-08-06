import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}


android {
    namespace = "teka.android.organiks_platform_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "teka.android.organiks_platform_android"
        minSdk = 26
        targetSdk = 34
        versionCode = 5
        versionName = "1.0-beta"



        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


        //load the values from .properties file
        val keystoreFile = project.rootProject.file("envVariables.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())
        val backendUrl = properties.getProperty("BACKEND_URL") ?: ""
        val geminiKey = properties.getProperty("geminiApiKey") ?: ""

        buildConfigField(
            type = "String",
            name = "BACKEND_URL",
            value = backendUrl
        )
        buildConfigField(
            type = "String",
            name = "GEMINI_API_KEY",
            value = geminiKey
        )


    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose-android:2.8.4")
    val compose_ui_version = "1.6.8"
    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation ("androidx.activity:activity-compose:1.9.1")
    implementation ("androidx.compose.material:material:1.6.4")
    implementation ("androidx.compose.ui:ui:$compose_ui_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")

    implementation ("androidx.compose.runtime:runtime:1.6.8")

    //material 3
    val material3_version = "1.2.1"
    implementation ("androidx.compose.material3:material3:$material3_version")
    //icons
    implementation ("androidx.compose.material:material-icons-extended:1.6.8")

    //work-manager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")
    // compose ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    //Room
    val room_version = "2.6.1"
    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Pager and Indicators - Accompanist
    implementation ("com.google.accompanist:accompanist-pager:0.28.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-work:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //retrofit and kotlinx dependencies
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //ktor
    val ktorVersion = "2.3.12"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    //Coroutines
    val coroutine_version = "1.7.0"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")

    //viewmodel and lifecycle scope
    val ktx_version = "2.8.4"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$ktx_version")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$ktx_version")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.8")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$ktx_version")

    //coil
    implementation ("io.coil-kt:coil-compose:2.5.0")
    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    // system UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    //gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")


    //Composable-Graphs
    implementation("com.github.jaikeerthick:Composable-Graphs:v1.2.3")

    //Y-Charts
    implementation("co.yml:ycharts:2.1.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //markdown support
    implementation("io.github.dakshsemwal:mdparserkitcore:1.0.1")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")


    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_ui_version")
    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_ui_version")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:$compose_ui_version")
}
