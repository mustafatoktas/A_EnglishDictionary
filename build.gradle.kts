plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    //kendi eklediğim
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}