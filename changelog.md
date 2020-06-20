# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

Since 2.0.0 versions change log same for all supported minecraft versions.

## [Unreleased]

## [2.0.1] - 2020-06-20

### Added
- Chinese simplified localization by @KuroNoSeiHai.

## [2.0.0] - 2020-06-08

### Added
- `configure-warps` command.

### Changed
- Providers resolution strategy for core module.
- Localization resolution strategy for core module.

### Fixed
- `WarpsConfiguration.kt` incorrect saving fixed.
- `WarpsSettingsConfiguration.kt` incorrect saving fixed.

## [2.0.0-SNAPSHOT.1] - 2020-06-03

### Added
- Chinese simplified translationOL`>:?`" added.
- updatev2.json added.
- Sending providers and localization added.
- Added resistance effect settings.
- `validateAndExecute` added.
- Warps limit checking added.
- Suggestions for `/warp` command.
- Suggestions for `/del-warp` command.

### Changed
- License now is MIT
- .gitignore synced with core module.
- gradle wrapper uploaded.
- .github files updated.
- gradle.properties updated.
- build.gradle updated.
- updateJSONURL uses v2 json.
- credits updated in mods.toml.
- Required core version now 2.X.
- Description updated.
- Warp command re-written.
- Set-warp command re-written.
- Del-warp command re-written.
- Localizations updated.

### Fixed
- Incorrect behavior with warps in different dimensions.

### Removed
- Internal assets removed.
- Internal documentation removed.
- Messaging.kt removed.
- Old source files removed.

## [1.15.2-1.0.1] - 2020-03-18

### Added
- Project Essentials dependencies added to `build.gradle`.
- Processing localization added.
- `Messaging.kt` implemented.
- `WarpCommand.kt`: Compatibility with new messaging added. 
- `SetWarpCommand.kt`: Compatibility with new messaging added. 
- `DelWarpCommand.kt`: Compatibility with new messaging added.
- `WarpCommand.kt`: compatibility with back command added.

### Changed
- Kotlin version updated to `1.3.70`.
- KotlinX Serialization version updated to `0.20.0`.
- Forge API version updated to `28.2.0`.

### Removed
- Essentials dependencies removed from `gradle.properties`.
- `curseforge` maven repository removed from repositories in `build.gradle`.

## [1.14.4-1.2.3] - 2020-03-18

### Added
- Project Essentials dependencies added to `build.gradle`.
- Processing localization added.
- `Messaging.kt` implemented.
- `WarpCommand.kt`: Compatibility with new messaging added. 
- `SetWarpCommand.kt`: Compatibility with new messaging added. 
- `DelWarpCommand.kt`: Compatibility with new messaging added.
- `WarpCommand.kt`: compatibility with back command added.

### Changed
- Kotlin version updated to `1.3.70`.
- KotlinX Serialization version updated to `0.20.0`.
- Forge API version updated to `28.2.0`.

### Removed
- Essentials dependencies removed from `gradle.properties`.
- `curseforge` maven repository removed from repositories in `build.gradle`.

## [1.15.2-1.0.0] - 2020-02-08

### Added
- Initial release.

## [1.14.4-1.2.2] - 2020-02-08

### Changed
- Uses `permissionAPIClassPath` from CoreAPI.
- Uses `cooldownAPIClassPath` from CoreAPI.

## [1.14.4-1.2.1] - 2020-01-27

### Fixed
- Incorrect operator level for warp commands executing.

## [1.14.4-1.2.0.0] - 2020-01-19

### Added
- Compatibility with core module `1.1.0.0` version.
- Compatibility with cooldown module `1.0.2.0` version.
- Sound effect after teleporting on any warp.
- Particles after teleporting on any warp.
- Resistance effect after teleporting on any warp.

### Changed
- Updated dependencies.
- Updated gradle wrapper version to `5.6.4`.

### Removed
- Redundant logger messages.

## [1.14.4-1.1.0.0] - 2020-01-12
  
### Added 
- Brazilian portuguese translations by [@rafaelcascaslho](https://github.com/rafaelcascaslho).
- German translations by [@BixelPitch](https://github.com/BixelPitch).
- Pull request template file by [@robbinworks](https://github.com/robbinworks).
- Issue Feature request template file.
- Bug issue template file.
- [FUNDING.yml](./.github/FUNDING.yml) file.
- This [CHANGELOG.md](./CHANGELOG.md) file.

### Changed
- Information for players.
- [de_de.json](./src/main/resources/assets/projectessentialswarps/lang/de_de.json) file formatted. 
- Now Permissions module not mandatory.
- Improved mod loading performance.
- Package name cleanup.
- Simplified JsonConfiguration in [WarpModelUtils.kt](./src/main/kotlin/com/mairwunnx/projectessentials/warps/models/WarpModelUtils.kt).
- [build.gradle](./build.gradle) file cleanup.
- Updated kotlin runtime version.
- Updated permissions module version.
- Updated forge and kotlinx serialization version.
- Gradle distribution changed to all in [gradle-wrapper.properties](./gradle/wrapper/gradle-wrapper.properties). 

### Fixed
- Typos in documentation.
- Incorrect removing warp.

### Removed
- Removed redundant java plugin.
- Redundant logger messages.

## [1.14.4-1.0.0.0] - 2019-10-14

### Added
- Initial release of Project Essentials Warps as Project Essentials part.
