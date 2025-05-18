import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "2.0.0-1.0.24"
}


android {
    namespace = "com.tasklist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tasklist"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs{
        getByName("debug"){
            val keystorePropertiesFile = rootProject.file("app/myKeyStore.properties")
            val keystoreProperties = Properties()
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
            keyAlias = keystoreProperties["keyAlias"].toString()
            keyPassword = keystoreProperties["keyPassword"].toString()
            storeFile = file(keystoreProperties["storeFile"].toString())
            storePassword = keystoreProperties["storePassword"].toString()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            resValue("string", "test_string", "Release")
            buildConfigField("String", "test_string", "\"Release\"")
            buildConfigField("Boolean", "test_boolean", "true")
            buildConfigField("int", "test_int", "10")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("localTest") {
            initWith(getByName("release"))
            isMinifyEnabled = false
            isShrinkResources = false
            resValue("string", "test_string", "localTest")
            buildConfigField("String", "test_string", "\"localTest\"")
            buildConfigField("Boolean", "test_boolean", "true")
            buildConfigField("int", "test_int", "20")
        }

        debug {
            initWith(getByName("release"))
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            resValue("string", "test_string", "debug")
            buildConfigField("String", "test_string", "\"debug\"")
            buildConfigField("Boolean", "test_boolean", "false")
            buildConfigField("int", "test_int", "0")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    flavorDimensions += listOf("AppFlavors")
    productFlavors {
        create("flavOne") {
            dimension = "AppFlavors"
        }
        create("flavTwo") {
            dimension = "AppFlavors"
        }
    }


}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    //Compose Destination
    val destination_version = "1.10.2"
    implementation("io.github.raamcosta.compose-destinations:animations-core:$destination_version")
    ksp("io.github.raamcosta.compose-destinations:ksp:$destination_version")

    //room
    val room_version = "2.6.1"
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-migration:$room_version")


    // Retrofit
    val retrofit_version = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.andretietz.retrofit:cache-extension:1.0.0")

    //DI
    val koin_version = "3.5.3"
    implementation("io.insert-koin:koin-android:$koin_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_version")
    testImplementation("io.insert-koin:koin-test:$koin_version")

    //UI
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    implementation("com.google.code.gson:gson:2.8.7")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")

    //Navigation
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    //Mokito
    testImplementation("org.mockito:mockito-core:5.8.0")
    //Coroutine Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}