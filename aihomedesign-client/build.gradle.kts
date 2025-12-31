plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")

    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktechMavenPublish)
}

kotlin {
    explicitApi()

    withSourcesJar()

    jvm()
    jvmToolchain(11)

    sourceSets {
        commonMain.dependencies {
            api(libs.coroutines.core)
            api(libs.ktor.client.core)
            implementation(libs.serialization.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization.json)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
            implementation(libs.ktor.client.mock)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(
        groupId = group.toString(),
        artifactId = providers.gradleProperty("POM_ARTIFACT_ID").get(),
        version = version.toString()
    )

    pom {
        name = providers.gradleProperty("POM_NAME").get()
        description = providers.gradleProperty("POM_DESCRIPTION").get()
        url = providers.gradleProperty("POM_URL").get()

        licenses {
            license {
                name = providers.gradleProperty("POM_LICENCE_NAME").get()
                url = providers.gradleProperty("POM_LICENCE_URL").get()
            }
        }

        developers {
            developer {
                id = providers.gradleProperty("POM_DEVELOPER_ID").get()
                name = providers.gradleProperty("POM_DEVELOPER_NAME").get()
                url = providers.gradleProperty("POM_DEVELOPER_URL").get()
            }
        }

        scm {
            url = providers.gradleProperty("POM_SCM_URL").get()
            connection = providers.gradleProperty("POM_SCM_CONNECTION").get()
            developerConnection = providers.gradleProperty("POM_SCM_DEV_CONNECTION").get()
        }
    }
}