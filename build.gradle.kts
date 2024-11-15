// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("com.google.gms.google-services") version "4.3.14" apply false // Especifica la versión
}

allprojects {
    // Esta sección se puede eliminar, ya que los repositorios ya están en el settings.gradle.kts
    // repositories {
    //     google()
    //     mavenCentral()
    // }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
