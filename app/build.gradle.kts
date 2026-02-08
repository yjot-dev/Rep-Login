plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.yjotdev.login"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.yjotdev.login.yjotdev.login"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "2.1"
        testInstrumentationRunner = "com.yjotdev.login.yjotdev.login.CustomTestRunner"
        // Variables globales en gradle
        val apiDomain = project.findProperty("APP_API_DOMAIN") as? String
            ?: error("La propiedad 'APP_API_DOMAIN' no se encontró en gradle.properties")
        val certPinIntermediate = project.findProperty("APP_CERT_PIN_INTERMEDIATE") as? String
            ?: error("La propiedad 'APP_CERT_PIN_INTERMEDIATE' no se encontró en gradle.properties")
        val certPinLeaf = project.findProperty("APP_CERT_PIN_LEAF") as? String
            ?: error("La propiedad 'APP_CERT_PIN_LEAF' no se encontró en gradle.properties")
        // Variables en BuildConfig
        buildConfigField("String", "API_DOMAIN", "\"$apiDomain\"")
        buildConfigField("String", "CERT_PIN_INTERMEDIATE", "\"$certPinIntermediate\"")
        buildConfigField("String", "CERT_PIN_LEAF", "\"$certPinLeaf\"")
    }
    signingConfigs {
        create("release") {
            keyAlias = project.findProperty("APP_KEY_ALIAS") as? String
            keyPassword = project.findProperty("APP_KEY_PASSWORD") as? String
            storePassword = project.findProperty("APP_STORE_PASSWORD") as? String
            storeFile = project.findProperty("APP_STORE_FILE")?.let { rootProject.file(it) }
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk {
                debugSymbolLevel = "FULL"
            }
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
        viewBinding = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        jniLibs {
            useLegacyPackaging = false
        }
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
}

dependencies {
    //IU
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //Retrofit
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.gson)
    implementation(libs.google.code.gson)
    //Logging Interceptor
    implementation(libs.squareup.okhttp3.logging.interceptor)
    //Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    //Test
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.dagger.hilt.android.testing)
    kspAndroidTest(libs.dagger.hilt.android.compiler)
}