# AI Home Design API client for Kotlin

[![Maven Central](https://img.shields.io/maven-central/v/org.souhub.aihomedesign/aihomedesign-client?color=blue&label=Download)](https://central.sonatype.com/namespace/org.souhub.aihomedesign)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![License](https://img.shields.io/github/license/souhub/aihomedesign-kotlin?color=yellow)](LICENSE)

Kotlin client for [AI Home Design API](https://doc.aihomedesign.com/) with multiplatform and coroutines capabilities.

## 📦 Setup

1. Install AI Home Design API Kotlin client by adding the following dependency to your `build.gradle` file:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation "org.souhub.aihomedesign:aihomedesign-client:0.1.0"
    runtimeOnly "io.ktor:ktor-client-okhttp:3.0.3" // choose an engine from https://ktor.io/docs/http-client-engines.html
}
```

2. Choose and add to your dependencies one of [Ktor's engines](https://ktor.io/docs/http-client-engines.html).

### Multiplatform

In multiplatform projects, add aihomedesign client dependency to `commonMain`, and choose an [engine](https://ktor.io/docs/http-client-engines.html) for each target.

### Maven

Gradle is required for multiplatform support, but there's nothing stopping you from using the jvm client in a Maven project. You still need to add to your dependencies one of [Ktor's engines](https://ktor.io/docs/http-client-engines.html).

<details>
 <summary>Setup the client with maven</summary>

```xml
<dependencies>
    <dependency>
        <groupId>org.souhub.aihomedesign</groupId>
        <artifactId>aihomedesign-client-jvm</artifactId>
        <version>0.1.0</version>
    </dependency>

    <dependency>
        <groupId>io.ktor</groupId>
        <artifactId>ktor-client-okhttp-jvm</artifactId>
        <version>3.0.3</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

</details>

## ⚡️ Getting Started

Create an instance of `AIHomeDesign` client:

```kotlin
val aiHomeDesign = AIHomeDesign(
    apiKey = "your-api-key"
)
```

Or you can create an instance of `AIHomeDesign` using a pre-configured `AIHomeDesignConfig`:

```kotlin
val config = AIHomeDesignConfig(
    apiKey = apiKey,
    timeout = 60.seconds,
    // additional configurations...
)

val aiHomeDesign = AIHomeDesign(config)
```

Use your `AIHomeDesign` instance to make API requests.

### Supported features

- [Get Spaces](https://doc.aihomedesign.com/api-endpoints/get-spaces)
- [Image Upload](https://doc.aihomedesign.com/api-endpoints/image-upload)
- [Submit Order](https://doc.aihomedesign.com/api-endpoints/submit-order)
  - [AI Item Removal](https://doc.aihomedesign.com/api-endpoints/submit-order/ai-item-removal)
  - [AI Item Removal Mask](https://doc.aihomedesign.com/api-endpoints/submit-order/ai-item-removal-mask)

## 📄 License

AI Home Design Kotlin API Client is an open-sourced software licensed under the [MIT license](LICENSE).
**This is an unofficial library, it is not affiliated with nor endorsed by AI Home Design**. Contributions are welcome.