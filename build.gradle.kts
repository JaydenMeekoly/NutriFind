// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

// Load secrets.properties file
val secretsProperties = java.util.Properties().apply {
    val secretsFile = rootProject.file("secrets.properties")
    if (secretsFile.exists()) {
        load(java.io.FileInputStream(secretsFile))
    } else {
        logger.warn("secrets.properties not found. Please create it with SPOONACULAR_API_KEY")
    }
}

// Make API key available to all modules
subprojects {
    extra["spoonacularApiKey"] = secretsProperties.getProperty("SPOONACULAR_API_KEY", "")
}