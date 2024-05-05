plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.20" apply false
    kotlin("kapt") version "1.9.20"
}





//buildscript {
//    ext {
//        compose_ui_version = '1.4.0'
//    }
//}// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    id 'com.android.application' version '8.2.2' apply false
//    id 'com.android.library' version '8.2.2' apply false
//    id 'org.jetbrains.kotlin.android' version '1.9.20' apply false
//    id 'com.google.dagger.hilt.android' version '2.48' apply false
//    id 'org.jetbrains.kotlin.plugin.serialization' version '1.9.20' apply false
////    id "org.jetbrains.kotlin.kapt" version "1.9.20"
//}
//
//task clean(type: Delete) {
//    delete rootProject.buildDir
//}