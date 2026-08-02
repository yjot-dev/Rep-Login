plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.yjotdev.login"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.yjotdev.login"
        minSdk = 24
        targetSdk = 36
        versionCode = 12
        versionName = "2.2"
        testInstrumentationRunner = "com.yjotdev.login.CustomTestRunner"
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
            //applicationIdSuffix = ""
            //signingConfig = signingConfigs.getByName("release")
            applicationIdSuffix = ".debug"
            isDebuggable = true
            buildConfigField("String", "API_BASE_URL", "\"${project.findProperty("API_BASE_URL_DEBUG")}\"")
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
            buildConfigField("String", "API_BASE_URL", "\"${project.findProperty("API_BASE_URL_RELEASE")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
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
    //Billing
    implementation(libs.android.billingclient.billing)
    //Retrofit
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.gson)
    implementation(libs.google.code.gson)
    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    //Logging Interceptor
    implementation(libs.squareup.okhttp3.logging.interceptor)
    //Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    //Test
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.dagger.hilt.android.testing)
    kspAndroidTest(libs.dagger.hilt.android.compiler)
}