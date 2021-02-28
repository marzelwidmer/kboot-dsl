plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // ######### Test Dependencies
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.kotest:kotest-runner-junit5:4.4.1")
    testImplementation ("io.kotest:kotest-assertions-core:4.4.1")
    testImplementation ("io.kotest:kotest-property:4.4.1")

}
