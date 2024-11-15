// build.gradle.kts en el módulo (app)
plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // Asegúrate de que esta línea esté habilitada
}

android {
    namespace = "com.example.gimnasio"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gimnasio"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true // Habilitar View Binding
        // dataBinding = true // Si necesitas habilitar Data Binding, descomenta esta línea
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
}

dependencies {
    // GOOGLE DEPENDENCIAS
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics")

    // DEFAULT
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //RECYCLERVIEW
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    //BIBLIOTECA PARA EL BORDE DE LAS IMAGENES
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.cardview:cardview:1.0.0")


    //GIFS
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.23")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

    //NUMBERPICKER
    implementation ("androidx.appcompat:appcompat:1.3.0")

    //GRAFICOS PARA MACROS DE GITHUB
    implementation ("com.github.PhilJay:MPAndroidChart:3.0.3")

}
