plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.6.10")
    implementation("dev.misfitlabs.kotlinguice4:kotlin-guice:1.4.0")
    implementation("io.github.microutils:kotlin-logging:1.7.6")
    implementation("com.sparkjava:spark-core:+")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("org.slf4j:jcl-over-slf4j:1.7.25")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.slf4j:jul-to-slf4j:1.7.25")
    implementation("com.google.inject:guice:4.2.0")
    implementation("com.beust:klaxon:5.0.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "theagainagain.MainKt"
}

tasks.register("stage") {
    dependsOn("installDist")
}