plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.saptrishi.hotelkotapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.saptrishi.hotelkotapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
        //Material design
    implementation(libs.design)
    implementation(libs.appcompat.v7)
    implementation(libs.recyclerview.v7)
    implementation(libs.cardview.v7)
    implementation (libs.palette.v7)
    implementation (libs.support.v4)
    implementation(libs.gson)
    implementation(libs.acra)
//    compile files("libs/com.google.guava_1.6.0.jar")
    implementation(libs.glide)
    implementation(libs.picasso)
    implementation(libs.kenburnsview)
//    implementation("com.github.PhilJay:MPAndroidChart:v3.0.2")
        //Loading animations
//    implementation("com.wang.avi:library:1.0.5")
    implementation(libs.library)
        //compile 'com.android.support.constraint:constraint-layout:1.1.3'
    implementation(libs.constraint.layout)
//    compile files("libs/com.android.volley-2015.05.28.jar")
    implementation(libs.volley)
}