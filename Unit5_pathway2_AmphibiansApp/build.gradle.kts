buildscript {
    extra.apply {
        set("lifecycle_version", "2.8.7")
        set("retrofit2_version", "2.11.0")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}