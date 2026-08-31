# Fix "SourceSet with name 'main' not found" and Build Errors

The project is currently missing the `kotlin-android` plugin, which is essential for compiling Kotlin code in an Android module. Additionally, the `compileSdk` configuration in `app/build.gradle.kts` uses an unrecognized `release(37)` function, and the IDE's attempt to run the `main` function in `Carrito.kt` fails because it cannot find the standard Gradle `main` source set.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/Magal/Programacion-en-Moviles/Semana-02/Lab-02/Lab02CarritoKotlin/gradle/libs.versions.toml)
- Add `kotlin-android` plugin definition.

#### [MODIFY] [root build.gradle.kts](file:///C:/Users/Magal/Programacion-en-Moviles/Semana-02/Lab-02/Lab02CarritoKotlin/build.gradle.kts)
- Add `kotlin-android` plugin to the top-level plugins block.

#### [MODIFY] [app build.gradle.kts](file:///C:/Users/Magal/Programacion-en-Moviles/Semana-02/Lab-02/Lab02CarritoKotlin/app/build.gradle.kts)
- Apply `kotlin-android` plugin.
- Fix `compileSdk` to use the standard `compileSdk = 35` (or a supported version).
- Add a `sourceSets` configuration to satisfy the IDE's Kotlin runner.

## Verification Plan

### Automated Tests
- Run Gradle sync to ensure the project configures correctly.
- Attempt to build the project using `./gradlew assembleDebug`.

### Manual Verification
- Verify that the `main` function in `Carrito.kt` can be executed by clicking the "Run" icon in the IDE.
