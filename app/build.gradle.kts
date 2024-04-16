import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ls.localsky"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ls.localsky"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testApplicationId = "com.ls.localsky"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val googleApiKey = properties.getProperty("GOOGLE_MAPS_API_KEY") ?: ""
        val pirateWeatherApi = properties.getProperty("PIRATE_WEATHER_API_KEY") ?: ""

        buildConfigField(
            type = "String",
            name = "GOOGLE_MAPS_API_KEY",
            value = "\"$googleApiKey\""
        )

        buildConfigField(
            type = "String",
            name = "PIRATE_WEATHER_API_KEY",
            value = "\"$pirateWeatherApi\""
        )

        manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = googleApiKey
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    //Compose
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended:1.6.4")
    implementation("androidx.compose.material:material:1.6.4")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")


    //Firebase
    //Firebase Auth
    val firebaseAuthVersion = "22.3.1"
    implementation("com.google.firebase:firebase-auth:$firebaseAuthVersion")

    //Firebase Firestore
    val firestoreVersion = "24.11.0"
    implementation("com.google.firebase:firebase-firestore:$firestoreVersion")
    // Firebase Storage
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-storage")

    //Gson
    implementation("com.google.code.gson:gson:2.10.1")

    //Rooms
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:2.6.1")

    //Google Maps
    implementation("com.google.maps.android:maps-compose:4.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

}

