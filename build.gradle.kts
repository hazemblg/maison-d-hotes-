plugins {
    id("com.android.application") version "8.13.1" apply false
    id("com.android.library") version "8.13.1" apply false
    kotlin("android") version "1.9.22" apply false
    kotlin("jvm") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.6" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}