package mohsin.reza.movieapp

object Android {
    const val buildTools = "28.0.3"
    const val compileSdk = 29
    const val minSdk = 17
    const val minSdkTV = 24
    const val targetSdk = 29
}

object Versions {
    const val appCompat = "1.0.2"
    const val auth0 = "1.19.0"
    const val constraintLayout = "1.1.3"
    const val coreTesting = "2.0.1"
    const val dagger = "2.24"
    const val espresso = "3.1.1"
    const val glide = "4.8.0"
    const val glideTransformation = "3.1.1"
    const val gradlePlugin = "3.5.3"
    const val gradleSpawn = "0.8.2"
    const val gradleVersions = "0.21.0"
    const val jodaTime = "2.9.9.4"
    const val junit = "4.12"
    const val junitExt = "1.1.0"
    const val kotlin = "1.3.61"
    const val ktx = "1.0.0"
    const val lifecycleExt = "2.0.0"
    const val livedataCore = "2.0.0"
    const val material = "1.0.0"
    const val mockk = "1.9"
    const val moshi = "1.6.0"
    const val okhttp3 = "3.10.0"
    const val okhttpLegacy = "2.7.5"
    const val recycler = "1.0.0"
    const val retrofit = "2.3.0"
    const val rxAndroid = "2.1.0"
    const val rxBinding = "2.1.1"
    const val rxKotlin = "2.2.0"
    const val spoon = "2.0.0-SNAPSHOT"
    const val spoonGradle = "1.5.0"
    const val timber = "4.7.1"
    const val testRunner = "1.1.1"
    const val testRules = "1.1.1"
}

object Libs {
    const val auth0 = "com.auth0.android:auth0:${Versions.auth0}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideTransformation = "jp.wasabeef:glide-transformations:${Versions.glideTransformation}"
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val jodaTime = "net.danlew:android.joda:${Versions.jodaTime}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExt}"
    const val livedataCore = "androidx.lifecycle:lifecycle-livedata-core:${Versions.livedataCore}"
    const val livedataRx = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycleExt}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    const val okhttpLegacy = "com.squareup.okhttp:okhttp:${Versions.okhttpLegacy}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
    const val okhttpLoggingLegacy = "com.squareup.okhttp:logging-interceptor:${Versions.okhttpLegacy}"
    const val okhttpUrlConnection = "com.squareup.okhttp3:okhttp-urlconnection:${Versions.okhttp3}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recycler}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxBinding = "com.jakewharton.rxbinding2:rxbinding:${Versions.rxBinding}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

}

object TestLibs {
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val junit = "junit:junit:${Versions.junit}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val runner = "androidx.test:runner:${Versions.testRunner}"
    const val rules = "androidx.test:rules:${Versions.testRules}"
    const val spoon = "com.squareup.spoon:spoon-client:${Versions.spoon}"
}
