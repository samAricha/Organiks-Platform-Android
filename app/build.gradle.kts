import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    id("dagger.hilt.android.plugin")
}


//def localProperties = new Properties()
//localProperties.load(new FileInputStream(rootProject.file("local.properties")))
//
////for testing purposes
//def envVariablesProperties = new Properties()
//envVariablesProperties.load(new FileInputStream(rootProject.file("envVariables.properties")))

//load the values from .properties file
//def keystoreFile = project.rootProject.file("envVariables.properties")
//def properties = Properties()
//properties.load(keystoreFile.inputStream())
//def backendUrl = properties.getProperty("BACKEND_URL") ?: ""

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



//        buildConfigField("String","GEMINI_API_KEY","\"" + localProperties['geminiApiKey'] + "\"")
//        buildConfigField("String","BACKEND_API_URL","\"" + localProperties['backendUrl'] + "\"")



//        buildConfigField(
//                type = "String",
//                name = "BACKEND_URL",
//                value = backendUrl
//        )

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
    val compose_ui_version = "1.4.0"
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.activity:activity-compose:1.8.2")
    implementation ("androidx.compose.material:material:1.6.4")
    implementation ("androidx.compose.ui:ui:$compose_ui_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")

    implementation ("androidx.compose.runtime:runtime:1.6.4")

    //material 3
    val material3_version = "1.1.1"
    implementation ("androidx.compose.material3:material3:$material3_version")
    //icons
    implementation ("androidx.compose.material:material-icons-extended:1.6.4")

    //work-manager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.4")
    // compose ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    //Room
    val room_version = "2.6.0"
    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Pager and Indicators - Accompanist
    implementation ("com.google.accompanist:accompanist-pager:0.28.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Dagger - Hilt
//    implementation ("com.google.dagger:hilt-android:2.50")
//    implementation ("androidx.hilt:hilt-work:1.2.0")
//    kapt ("com.google.dagger:hilt-android-compiler:2.48")
//    kapt ("androidx.hilt:hilt-compiler:1.2.0")
//    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //retrofit and kotlinx dependencies
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    //ktor
    val ktorVersion = "2.3.6"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    //Coroutines
    val coroutine_version = "1.7.0"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")

    //viewmodel and lifecycle scope
    val ktx_version = "2.6.1"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$ktx_version")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$ktx_version")

    implementation ("androidx.compose.runtime:runtime-livedata:1.6.4")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    //coil
    implementation ("io.coil-kt:coil-compose:2.5.0")
    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    // system UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    //gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.2.2")

    //Composable-Graphs
    implementation("com.github.jaikeerthick:Composable-Graphs:v1.2.3")

    //Y-Charts
    implementation("co.yml:ycharts:2.1.0")







    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_ui_version")
    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_ui_version")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:$compose_ui_version")
}
