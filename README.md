# API Dependency
To add the MDTConfig API in your plugin, make sure to add the JitPack repository and the dependency

## Gradle (Groovy DSL)
Add the dependency and repo in `build.gradle`:
```gradle
repositories {
    // Your other repos
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    // Your other dependencies
    compileOnly 'com.github.MDT168:MDTConfig:v1.0.0'
}
```

## Gradle (Kotlin DSL)
Add the dependency and repo in `build.gradle.kts`:
```gradle
repositories {
    // Your other repos
    maven("https://jitpack.io")
}

dependencies {
    // Your other dependencies
    compileOnly("com.github.MDT168:MDTConfig:v1.0.0")
}
```

## Maven
Add the dependency and repo in `pom.xml`:
```xml
<repositories>
    <!-- Your other repos -->
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- Your other dependencies -->
    <dependency>
        <groupId>com.github.MDT168</groupId>
        <artifactId>MDTConfig</artifactId>
        <version>v1.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
# Final Setup
After you you have added the `gradle/maven` dependency, now you need to depend on `MDTConfig` in your plugin yml file. The next part covers how to do so in both **Spigot** and **PaperMC**:

## Spigot (`plugin.yml`)
Find your `plugin.yml` file in the `resources` directory of your project, depend on MDTConfig:
```yml
depend: [MDTConfig] 
```
If you already had `depend` before, just add `MDTConfig` to the list

## PaperMC (`paper-plugin.yml`)
Find your `paper-plugin.yml` file in the `resources` directory of your project, depend on MDTConfig:
```yml
dependencies:
  server:
    MDTConfig:
      load: AFTER
      required: true
```
Or you can choose Boostrap if you want an early load

# Getting Started
To know how to use MDTConfig and get started right away, visit The [Getting Started](https://github.com/MDT168/MDTConfig/wiki/Getting-Started) Wiki Page