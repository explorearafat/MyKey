# Orbit Board 🪐 — First Stable Release

[![Version](https://img.shields.io/badge/Version-v1.0.0%20Stable-E5252A?style=flat-square)](https://github.com/fahitakrim/Orbit-Board/releases/tag/v1.0.0)
[![Download APK](https://img.shields.io/badge/Download-APK%20(ARM64%20%26%20Universal)-success?style=flat-square&logo=android)](https://github.com/fahitakrim/Orbit-Board/releases/tag/v1.0.0)
[![Android](https://img.shields.io/badge/Android-7.0%2B%20(API%2024%20--%2035)-blue.svg?style=flat-square)](https://developer.android.com)
[![Design](https://img.shields.io/badge/Design-Nothing%20OS%20%26%20Bauhaus-black?style=flat-square)](https://nothing.tech)
[![Privacy](https://img.shields.io/badge/Privacy-100%25%20Offline-green.svg?style=flat-square)](https://github.com/fahitakrim/Orbit-Board)
[![License](https://img.shields.io/badge/License-Apache%202.0%20%2F%20Open%20Source-orange.svg?style=flat-square)](LICENSE.md)

**Orbit Board** is an aesthetic, privacy-first offline Android keyboard engineered with a **Nothing OS & Bauhaus-inspired design language**. Featuring an exclusive **Matte Orbit Theme**, dynamic per-key matching popup bubble previews, and lightning-fast phonetic Bengali typing (Avro & Ridmik compatible), Orbit Board delivers an elevated, fluid, and distraction-free typing experience.

---

### 📦 [Download Official v1.0.0 Stable Release](https://github.com/fahitakrim/Orbit-Board/releases/tag/v1.0.0)

| Package | Architecture | Direct Download |
|:---|:---|:---|
| **ARM64 APK** *(Recommended)* | `arm64-v8a` (Modern Phones) | [⬇️ Download ARM64 APK](https://github.com/fahitakrim/Orbit-Board/releases/download/v1.0.0/orbit-board-v1.0.0-arm64.apk) |
| **Universal APK** | All Android CPU Architectures | [⬇️ Download Universal APK](https://github.com/fahitakrim/Orbit-Board/releases/download/v1.0.0/orbit-board-v1.0.0-universal.apk) |

---

## ✨ Key Features (v1.0.0 Stable)

### 🎨 Matte Orbit Theme & Bauhaus Aesthetics
- **Tactile Matte Palette**: Curated with warm Bauhaus tones that are easy on the eyes:
  - 🟡 **Matte Warm Ochre Yellow** (`#E2B438`)
  - 🔴 **Matte Carmine Red** (`#C23636`)
  - 🔵 **Matte Cobalt Slate Blue** (`#2A5288`)
  - ⚪ **Matte Chalk White** (`#EAE6E1`)
  - ⬛ **Matte Graphite Dark** (`#1C1D20`) & **AMOLED Pure Black** (`#000000`)
- **Dynamic Per-Key Popup Bubbles**: When pressing colored or accent keys, the key preview bubble dynamically matches the key's background and high-contrast text color, ensuring a cohesive and premium look.

### 🇧🇩 Seamless Phonetic Bengali Typing (Avro & Ridmik Style)
- **Direct Bengali Output**: Keystrokes instantly render corresponding Bengali phonetic characters without Latin-to-Bengali flickering.
- **Smart Candidate Suggestions**: Real-time phonetic suggestions with multi-candidate words.
- **Rock-Solid Long-Press**: Long-pressing keys for numbers or special symbols behaves reliably across all layouts without crashing.

### 🔒 100% Offline & Privacy-First
- Zero internet access required — no network permissions requested in manifest.
- Zero analytics, telemetry, or cloud logging. Keystrokes and clipboard data strictly remain on your device.

### ⚡ Fluid Gestures & Customization
- Gesture / Glide typing with real-time trace path.
- Configurable keyboard height, dedicated number row, and custom symbol layouts.
- Tactile haptic feedback engine with custom vibration duration.

---

## 🛠️ Tech Stack & Architecture

- **Language:** Java & Kotlin
- **Native Engine:** C++ (JNI Native Input Engine & Dictionary Decoder)
- **Target SDK:** Android 34 (UpsideDownCake) / Minimum SDK: Android 24 (Nougat)
- **Design Tokens:** Nothing OS Dot-Matrix & Bauhaus Aesthetic Standards
- **Build System:** Gradle 8.2

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug / Koala or newer
- JDK 17+
- Android SDK 34 (NDK installed for native C++ components)

### Build & Run
```bash
# Clone the repository
git clone https://github.com/fahitakrim/Orbit-Board.git
cd Orbit-Board

# Build Stable Release APKs
./gradlew assembleStableRelease

# Generated APKs will be located in:
# build/outputs/apk/stable/release/
```

---

## 👨‍💻 Developer

- **Developer:** [Fahim Takrim](https://github.com/fahitakrim)
- **X (Twitter):** [@TakrimFahim](https://x.com/TakrimFahim)

---

## 📄 License

Orbit Board is distributed under the Apache License 2.0 and open-source licenses. See [LICENSE.md](LICENSE.md) and [NOTICE](NOTICE) for details.
