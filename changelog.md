# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

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
- Incorrect removing warp.

### Removed
- Removed redundant java plugin.
- Redundant logger messages.

## [1.14.4-1.0.0.0] - 2019-10-14

### Added
- Initial release of Project Essentials Warps as Project Essentials part.
