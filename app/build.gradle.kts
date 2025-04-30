plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version libs.versions.ksp.get()
    id("androidx.navigation.safeargs.kotlin") // теперь он будет найден
}

android {
    // Основные настройки приложения
    namespace = "com.example.studentdiary"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.studentdiary"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Минификация кода отключена для релизной сборки
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Настройки совместимости Java и Kotlin
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Основные библиотеки Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Тестирование
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Room (для работы с базой данных)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Coroutines (для работы с асинхронным кодом)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Lifecycle (для ViewModel, LiveData)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Работа с окнами и их элементами
    implementation(libs.androidx.window)

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

    // Fragment (для работы с фрагментами)
    implementation("androidx.fragment:fragment-ktx:1.5.7")

    // MPAndroidChart (для работы с круговыми диаграммами)
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Navigation (для работы с графом навигации)
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
}