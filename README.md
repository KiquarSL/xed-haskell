# Haskell Support

Adds support for Haskell and Cabal (Build system).

## Installation

Install the extension through the Xed-Editor's extension marketplace, and you're ready to go! Alternatively, you can download the latest release ZIP file and install it via Settings > Extensions > Install from storage.

For install Haskell compiler and build system:
``bash
apt install ghc cabal-install
```

Debug build:
```bash
./gradlew assembleDebug
./gradlew :app:createFinalZip
```

Release build:
```bash
./gradlew assembleRelease
./gradlew :app:createFinalZip
```
