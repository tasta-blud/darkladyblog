group = "darkladyblog"
version = "1.0.0"

val KEY_DEVELOPMENT: String = "development"

val CLIENT_PORT: Int = 3000
val SERVER_URL: String = "http://localhost:8080"
val ENDPOINTS: List<String> = listOf("/api", "/rpc", "/webjars", "/static")

val MAIN_CLASS_NAME: String = "darkladyblog.darkladyblog.server.MainKt"

val LANGUAGES_LIST: List<String> = listOf("en", "ru")

val isDevelopment: Boolean = System.getenv("ORG_GRADLE_PROJECT_$KEY_DEVELOPMENT")?.toBoolean()
    ?: project.ext.has(KEY_DEVELOPMENT)

plugins {
    alias(libs.plugins.org.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.de.comahe.i18n4k)
    alias(libs.plugins.dev.kilua.rpc)
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}
kotlin {
    js {
        browser {
            commonWebpackConfig {
                mode = if (isDevelopment)
                    org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.DEVELOPMENT
                else
                    org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.PRODUCTION
                devServer =
                    (devServer ?: org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer()).copy(
                        port = CLIENT_PORT,
                        proxy = mutableListOf(
                            org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer.Proxy(
                                context = ENDPOINTS.toMutableList(),
                                target = SERVER_URL,
                            )
                        )
                    )
                showProgress = true
                sourceMaps = true
                devtool = org.jetbrains.kotlin.gradle.targets.js.webpack.WebpackDevtool.INLINE_SOURCE_MAP
            }
        }
    }.binaries.executable()

    jvm {
        @OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass = MAIN_CLASS_NAME
            args(listOf(KEY_DEVELOPMENT, isDevelopment))
        }
    }

    sourceSets {
        commonMain {
            tasks.withType<com.google.devtools.ksp.gradle.KspTaskMetadata> {
                kotlin {
                    srcDir(destinationDirectory)
                }
            }
            kotlin {
                srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            }
            tasks.withType<com.google.devtools.ksp.gradle.KspTaskMetadata> {
                kotlin.srcDir(destinationDirectory)
            }

            dependencies {
                api(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)

                implementation(libs.io.insert.koin.koin.core)
                api(libs.io.insert.koin.koin.annotations)


                implementation(libs.dev.fritz2.core)
                implementation(libs.dev.fritz2.headless) // optional

                api(libs.de.comahe.i18n4k.i18n4k.core)

                api(libs.org.jetbrains.kotlinx.kotlinx.datetime)

                implementation(project.dependencies.platform(libs.org.kotlincrypto.hash.bom))
                api(libs.org.kotlincrypto.hash.md)
                api(libs.org.kotlincrypto.hash.sha1)
                api(libs.org.kotlincrypto.hash.sha2)
                api(libs.org.kotlincrypto.hash.sha3)
                api(libs.org.kotlincrypto.hash.blake2)

                api(libs.org.jetbrains.kotlin.wrappers.kotlin.css)

                api(libs.io.ktor.ktor.http)

//                implementation(libs.dev.kilua.kilua.rpc.ktor.ktor)
                implementation(libs.dev.kilua.kilua.rpc.ktor.koin)
            }
        }
        commonTest {
            dependencies {
                api(libs.org.jetbrains.kotlin.test)
            }
        }

        jsMain {
            tasks.withType<com.google.devtools.ksp.gradle.KspTaskMetadata> {
                kotlin.srcDir(destinationDirectory)
            }
            dependencies {
                implementation(libs.dev.fritz2.core)
                implementation(libs.dev.fritz2.serialization)
                implementation(libs.dev.fritz2.headless)

                implementation(libs.org.markdownj.markdownj.core)

                implementation(libs.org.webjars.npm.editor.md)
                implementation(libs.com.vladsch.flexmark.flexmark)
                implementation(libs.com.ibm.mdfromhtml.remark)
                implementation(libs.org.webjars.jquery)
            }
        }
        jsTest {
            dependencies {
                implementation(libs.org.jetbrains.kotlin.test)
            }
        }

        jvmMain {
            tasks.withType<com.google.devtools.ksp.gradle.KspTaskMetadata> {
                kotlin.srcDir(destinationDirectory)
            }
            kotlin {
                srcDir("build/generated/ksp/jvm/jvmMain/kotlin")
            }
            dependencies {
                implementation(libs.dev.fritz2.core)
                implementation(libs.dev.fritz2.serialization)
                implementation(libs.dev.fritz2.headless)

                implementation(libs.io.ktor.ktor.server.core)
                implementation(libs.io.ktor.ktor.server.websockets)
                implementation(libs.io.ktor.ktor.serialization.kotlinx.json)
                implementation(libs.io.ktor.ktor.server.content.negotiation)
                implementation(libs.io.ktor.ktor.server.html.builder)
                implementation(libs.io.ktor.ktor.server.call.logging)
                implementation(libs.io.ktor.ktor.server.call.id)
                implementation(libs.io.ktor.ktor.server.partial.content)
                implementation(libs.io.ktor.ktor.server.forwarded.header)
                implementation(libs.io.ktor.ktor.server.default.headers)
                implementation(libs.io.ktor.ktor.server.cors)
                implementation(libs.io.ktor.ktor.server.conditional.headers)
                implementation(libs.io.ktor.ktor.server.compression)
                implementation(libs.io.ktor.ktor.server.caching.headers)
                implementation(libs.io.ktor.ktor.server.webjars)
                implementation(libs.io.ktor.ktor.server.host.common)
                implementation(libs.io.ktor.ktor.server.status.pages)
                implementation(libs.io.ktor.ktor.server.resources)
                implementation(libs.io.ktor.ktor.server.request.validation)
                implementation(libs.io.ktor.ktor.server.double.receive)
                implementation(libs.io.ktor.ktor.server.auto.head.response)
                implementation(libs.io.ktor.ktor.server.sessions)
                implementation(libs.io.ktor.ktor.server.csrf)
                implementation(libs.io.ktor.ktor.server.auth)
                implementation(libs.io.ktor.ktor.server.auth.jwt)
                implementation(libs.io.ktor.ktor.server.netty)
                implementation(libs.io.ktor.ktor.server.config.yaml)

                implementation(libs.io.ktor.ktor.client.core)
                implementation(libs.io.ktor.ktor.client.apache)

                implementation(libs.io.github.flaxoos.ktor.server.task.scheduling.core)
                implementation(libs.io.github.flaxoos.ktor.server.task.scheduling.jdbc)

                implementation(libs.io.insert.koin.koin.core)
                implementation(libs.io.insert.koin.koin.ktor)
                implementation(libs.io.insert.koin.koin.logger.slf4j)
                implementation(libs.io.insert.koin.koin.annotations)

                implementation(libs.org.postgresql.postgresql)
                implementation(libs.com.h2database.h2)

                implementation(libs.org.jetbrains.exposed.exposed.core)
                implementation(libs.org.jetbrains.exposed.exposed.jdbc)
                implementation(libs.org.jetbrains.exposed.exposed.dao)
                implementation(libs.org.jetbrains.kotlinx.kotlinx.html)

                implementation(libs.ch.qos.logback.logback.classic)

                implementation(libs.org.markdownj.markdownj.core)

                implementation(libs.org.webjars.npm.editor.md)
                implementation(libs.com.vladsch.flexmark.flexmark)
                implementation(libs.com.ibm.mdfromhtml.remark)
                implementation(libs.org.webjars.jquery)
            }
        }
        jvmTest {
            dependencies {
                implementation(libs.org.jetbrains.kotlin.test)
                implementation(libs.io.ktor.ktor.server.test.host)
                implementation(libs.org.jetbrains.kotlin.kotlin.test.junit)
            }
        }
    }
}

dependencies {
    kspCommonMainMetadata(libs.dev.fritz2.lenses.annotation.processor)
    add("kspCommonMainMetadata", libs.io.insert.koin.koin.ksp.compiler)
    add("kspJvm", libs.io.insert.koin.koin.ksp.compiler)
    add("kspJs", libs.io.insert.koin.koin.ksp.compiler)
}

ksp {
    arg("KOIN_DEFAULT_MODULE", "false")
}

i18n4k {
    sourceCodeLocales = LANGUAGES_LIST
    inputDirectory = "src/commonMain/resources"
}

tasks.generateI18n4kFiles {
    dependsOn("kspCommonMainKotlinMetadata", "kspKotlinJs", "kspKotlinJvm")
}

tasks.withType<com.google.devtools.ksp.gradle.KspAATask> {
    if (name != "kspCommonMainKotlinMetadata") dependsOn("kspCommonMainKotlinMetadata")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>> {
    dependsOn("kspCommonMainKotlinMetadata", "kspKotlinJs", "kspKotlinJvm")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("kspCommonMainKotlinMetadata", "kspKotlinJs", "kspKotlinJvm")
}

tasks.register<Copy>("copyJsToJvm") {
    val webpackTask =
        tasks.named<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>(
            if (isDevelopment) "jsBrowserDevelopmentWebpack" else "jsBrowserProductionWebpack"
        )
    dependsOn("jsBrowserDistribution", webpackTask)
    from(webpackTask.map { it.mainOutputFile.get().asFile })
    from("${layout.buildDirectory.get()}/dist/js/productionExecutable")
    include("**")
    into("${layout.buildDirectory.get()}/processedResources/jvm/main/static")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named("jvmProcessResources") {
    dependsOn("copyJsToJvm")
}

tasks.named<Jar>("jvmJar") {
    dependsOn(
        "jvmMainClasses",
        tasks.named<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>(
            if (isDevelopment) "jsBrowserDevelopmentWebpack" else "jsBrowserProductionWebpack"
        )
    )
    manifest {
        attributes("Main-Class" to MAIN_CLASS_NAME)
    }
    from(
        configurations.getByName("jvmRuntimeClasspath").map {
            if (it.isDirectory) it else zipTree(it)
        },
        kotlin.targets["jvm"].compilations.getByName("main").output.classesDirs
    )
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register("stage") {
    dependsOn(tasks.named("installDist"))
}

tasks.withType<JavaExec>().configureEach {
    classpath(tasks.named<Jar>("jvmJar"))
}

plugins.withType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin::class.java) {
    the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        yarnLockMismatchReport = org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport.WARNING
        reportNewYarnLock = true
        yarnLockAutoReplace = true
    }
}
