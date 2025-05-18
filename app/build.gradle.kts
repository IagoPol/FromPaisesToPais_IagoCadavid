plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
}
// al quitar las dependencias de room y volver a ponerlas,
// me salía un error cuya solución fue poner esto:
configurations.all{
    exclude(group = "com.intellij", module = "annotations")
}

android {
    namespace = "local.iago.paises"
    compileSdk = 35

    defaultConfig {
        applicationId = "local.iago.paises"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // por defecto (suficientes para ejecutar un recycler view -curso ANT youtube hasta vídeo 6-)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // añadidas por mí:
    // retrofit:
    // implementation(libs.retrofit) // https://square.github.io/retrofit/ sección Download
    // implementation(libs.converter.gson)
    // glide:
    // implementation(libs.glide)
    // implementation(libs.ksp)
    // navegación entre fragmentos:
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //ROOM:
    implementation(libs.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.androidx.legacy.support.v4)
    //RV:
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.recyclerview.selection)
    // ViewModel:
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // Corutinas:
    implementation(libs.kotlinx.coroutines.android)
}