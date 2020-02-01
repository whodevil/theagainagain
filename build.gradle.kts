plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    id("org.jetbrains.kotlin.plugin.allopen").version("1.3.61")
    application
    groovy
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
    implementation("commons-lang:commons-lang:+")
    compile("cglib:cglib:2.2")

    testImplementation("org.spockframework:spock-core:1.0-groovy-2.4")
    testImplementation("org.codehaus.groovy:groovy-all:2.4.12")
}

application {
    mainClassName = "theagainagain.MainKt"
}

tasks {

    register<Exec>("npmClean") {
        workingDir("ui")
        commandLine("rm", "-r", "build")
    }

    register<Exec>("npmBuild"){
        workingDir("ui")
        commandLine("yarn", "build")
    }

    register("stageVersion") {
        dependsOn("installDist")
        val version: String = System.getenv("SOURCE_VERSION") ?: "r999"
        doLast {
            File("$buildDir", "version").writeText(version)
        }
    }

    register("stage") {
        dependsOn("test", "stageVersion", "npmBuild")
    }

    named("clean") {
        dependsOn("npmClean")
    }
}

allOpen {
    annotation("theagainagain.OpenForTesting")
}
