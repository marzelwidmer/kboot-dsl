plugins {
    kotlin("jvm") version "1.4.31" apply false
    id("org.jmailen.kotlinter") version "3.3.0"
    id("org.sonarqube") version "3.0"
    base
    idea
    `java-library`
    `maven-publish`
    jacoco
}

val version: String by extra
val group: String by extra
val projectDescription: String by extra
val projectName: String by extra
val projectRepository: String by extra
val developerId: String by extra
val developerName: String by extra
val developerEmail: String by extra

fun Project.envConfig() = object : kotlin.properties.ReadOnlyProperty<Any?, String?> {
    override fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): String? =
        if (extensions.extraProperties.has(property.name)) {
            extensions.extraProperties[property.name] as? String
        } else {
            System.getenv(property.name)
        }
}

subprojects {
    group = group
    version = version

    apply(plugin = "java")
    apply(plugin = "jacoco")
    apply(plugin = "org.jmailen.kotlinter")
    apply(plugin = "maven-publish")

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }

    kotlinter {
        ignoreFailures = true
        indentSize = 4
        reporters = arrayOf("checkstyle", "plain", "html")
        experimentalRules = false
        disabledRules = arrayOf("no-wildcard-imports")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            languageVersion = "1.4"
            apiVersion = "1.4"
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    sonarqube

    tasks.jacocoTestReport {
        reports {
            xml.isEnabled = true
            csv.isEnabled = false
            html.destination = file("$buildDir/jacocoHtml")
        }
    }

    tasks.jacocoTestCoverageVerification {
        violationRules {
            rule {
                enabled = true
                element = "CLASS"
                excludes = listOf("com.jacoco.dto.*")
                limit {
                    counter = "BRANCH"
                    minimum = 0.0.toBigDecimal()
                }
            }
        }
    }

    tasks.test {
        // report is always generated after tests run
        finalizedBy(tasks.jacocoTestReport)
        finalizedBy(tasks.jacocoTestCoverageVerification)
    }

    tasks.withType<Delete> {
        doFirst {
            delete("~/.m2/repository/ch/keepcalm/dsl/")
        }
    }
}
