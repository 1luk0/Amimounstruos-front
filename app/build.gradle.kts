import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
}

android {
  namespace = "com.app.amimounstruos"
  compileSdk = 35

  // 🔹 Lee local.properties aquí, no dentro de defaultConfig
  val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
      file.inputStream().use { load(it) }
    }
  }

  defaultConfig {
    applicationId = "com.app.amimounstruos"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    // 🔹 Crea la constante BACKEND_URL
    buildConfigField(
      "String",
      "BACKEND_URL",
      "\"${localProperties.getProperty("backend.url", "http://192.168.215.26:3333/")}\""
    )
  }

  buildFeatures {
    buildConfig = true
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
  implementation(libs.retrofit)
  implementation(libs.retrofit.gson)
}
