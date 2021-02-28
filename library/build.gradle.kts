plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.31"
    application
    idea
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    // ######### Test Dependencies
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    testImplementation("io.mockk:mockk:1.10.6")
    testImplementation("org.amshove.kluent:kluent:1.65")

    testImplementation("io.kotest:kotest-runner-junit5:4.4.1")
    testImplementation ("io.kotest:kotest-assertions-core:4.4.1")
    testImplementation ("io.kotest:kotest-property:4.4.1")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
