plugins {
    id("com.android.application") version "8.13.1" apply false
    id("com.android.library") version "8.13.1" apply false
    kotlin("android") version "2.0.0" apply false
    kotlin("jvm") version "2.0.0" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false  // ✅ Ajouté
    id("androidx.navigation.safeargs.kotlin") version "2.7.6" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}