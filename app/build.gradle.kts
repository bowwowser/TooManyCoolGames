plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30
    defaultConfig {
        applicationId = "com.example.toomanycoolgames"
        minSdk = 24
        targetSdk = 30
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
//
//    testOptions {
//        unitTests.isIncludeAndroidResources = true
//    }
}

dependencies {
    // Core
    val appcompatVersion = "1.3.1"
    val coreVersion = "1.6.0"
    val kotlinVersion = "1.5.20"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.core:core-ktx:$coreVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")

    // DI
    val hiltVersion = "2.38.1"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    // Data
    val roomVersion = "2.3.0"
    val igdbApiVersion = "1.0.4"
    val resultVersion = "4.0.0"
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation("com.github.husnjak:IGDB-API-JVM:$igdbApiVersion")
    implementation("com.github.kittinunf.result:result:$resultVersion")
    implementation("com.github.kittinunf.result:result-coroutines:$resultVersion")

    // UI
    val fragmentVersion = "1.3.6"
    val constraintLayoutVersion = "2.1.0"
    val lifecycleVersion = "2.3.1"
    val navigationVersion = "2.3.5"
    val recyclerViewVersion = "1.2.1"
    val viewPager2Version = "1.0.0"
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("androidx.recyclerview:recyclerview:$recyclerViewVersion")
    implementation("androidx.viewpager2:viewpager2:$viewPager2Version")

    val glideVersion = "4.12.0"
    val materialVersion = "1.4.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")
    implementation("com.google.android.material:material:$materialVersion")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("androidx.test.ext:junit-ktx:1.1.3")
    testImplementation("org.robolectric:robolectric:4.6")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Misc.
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

kapt {
    correctErrorTypes = true
    // look into this more (https://stackoverflow.com/a/68254606)
    javacOptions {
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
    }
}