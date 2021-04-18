plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.example.toomanycoolgames"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        allWarningsAsErrors = true
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    lintOptions {
        disable("ObsoleteSdkInt", "UnusedResources")
    }
}

dependencies {
    val roomVersion = "2.3.0-beta01"
    val lifecycleVersion = "2.3.0"
    val glideVersion = "4.12.0"

    // Core
    implementation("androidx.appcompat:appcompat:1.3.0-rc01")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.fragment:fragment-ktx:1.3.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.31")

    // DI
    val hiltVersion = "2.32-alpha"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
    // TODO figure out how to avoid overeager build error w/ below
//    implementation("com.google.dagger:hilt-android:${rootProject.extra.get("hilt_version")}")
//    kapt("com.google.dagger:hilt-compiler:${rootProject.extra.get("hilt_version")}")

    // Data
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation("com.github.husnjak:IGDB-API-JVM:1.0.1")
    implementation("com.google.protobuf:protobuf-java:3.13.0")
    implementation("com.github.kittinunf.result:result:4.0.0")
    implementation("com.github.kittinunf.result:result-coroutines:4.0.0")

    // UI
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    // TODO check later if this is synced up again
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.recyclerview:recyclerview:1.2.0")

    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:${glideVersion}")
    implementation("com.google.android.material:material:1.3.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
