# Folder structure — MyKey

The working folder holds just two top-level entries: `.myto/` (Myto's own project memory — ignore it) and `Orbit-Board/`, the actual project (~148 MB).

## Orbit-Board — an offline Android keyboard (Gradle, Java + C++/JNI)

A rebrand and continuation of FUTO Keyboard (hence the retained `org.futo.inputmethod` package namespace), shipped as **MyKey** with a Nothing OS / Bauhaus matte design, 25 themes, 7 keyboard fonts, phonetic Bengali (Avro, Jatiyo, Probhat) typing and on-device Whisper voice input.

```
Orbit-Board/                                   148M
├── build.gradle, settings.gradle, gradle.properties
├── gradlew, gradlew.bat, gradle/wrapper/
├── README.md, LICENSE.md, NOTICE, proguard-rules.pro
│
├── java/                                49M  ← main Android app module (369 .java files)
│   ├── src/org/futo/inputmethod/
│   │   ├── keyboard/            layouts & key rendering (emoji/, internal/)
│   │   ├── latin/               core IME logic — 22 subpackages:
│   │   │     accounts bengali capture define inputlogic makedict network
│   │   │     payment permissions personalization settings spellcheck
│   │   │     suggestions touchinputconsumer userdictionary utils xlm
│   │   │     uix/{actions,settings,theme,voiceinput}
│   │   ├── accessibility/ announcements/ compat/
│   │   ├── dictionarypack/ event/ updates/ inputmethodcommon/
│   ├── res/                     ~140 locale/qualifier values-* dirs, 13 layouts,
│   │                            drawable/mipmap dpi buckets, anim, color, font, xml
│   ├── playstore/  stable/      build-flavor manifests + sources
│   └── test/src/                app-module unit tests
│
├── common/                             124K  ← shared Java lib (annotations, latin/common)
│
├── native/                              15M  ← C++ engines over JNI (447 files)
│   ├── jni/                     JNI bridges: BinaryDictionary, ProximityInfo,
│   │                            xlm_{AdapterTrainer,LanguageModel,ModelInfoLoader},
│   │                            WhisperGGML
│   │   ├── src/dictionary/      binary dict format: header, interface, property,
│   │   │                        structure/{backward/v402, pt_common, v2, v4}
│   │   ├── src/suggest/         the gesture/typing engine:
│   │   │                        core/{dicnode,dictionary,layout,policy,result,session}
│   │   │                        policyimpl/{gesture,typing,utils}
│   │   ├── src/ggml/            Whisper inference backend
│   │   ├── src/sentencepiece/   tokenizer
│   │   ├── src/third_party/     absl, darts_clone, esaxx, protobuf-lite
│   │   └── Android.mk / Application.mk / Android.bp
│   └── dicttoolkit/             offline dictionary builder CLI (23 files)
│
├── dictionaries/                        28M  ← 29 locale wordlists (.combined.gz) + emoji lists
│
├── libs/                               4.6M  ← prebuilt: glyph-developer-sdk.jar (Nothing Glyph API),
│                                               tensorflow-lite(.support).aar, pocketfft, vad
│
├── tests/                              2.9M  ← 287 JVM unit tests
│   └── src/org/futo/inputmethod/{keyboard/layout/*, latin/*, compat}
│
├── tools/                              699K  ← EditTextVariations test app, dicttool,
│                                               make-keyboard-text-py (locale generator)
│
└── voiceinput-shared/                  1.3M  ← Gradle module for shared Whisper voice input
```

## Where to go for what

| You want to change... | Go to |
|---|---|
| A key layout, key visuals, theme | `java/src/org/futo/inputmethod/keyboard/` + `java/res/values*/` |
| Typing / suggestion / autocorrect behaviour | `java/src/org/futo/inputmethod/latin/` |
| The gesture & typing engine itself | `native/jni/src/suggest/` |
| The binary dictionary format | `native/jni/src/dictionary/` |
| A language wordlist | `dictionaries/` |
| Voice input | `voiceinput-shared/` + `latin/uix/voiceinput/` + `native/jni/src/ggml/` |
| Tests | `tests/`, run with `./gradlew test` |

## Package-name key

All of these are the same app, split across modules:

- `org.futo.inputmethod.*` — upstream FUTO Keyboard namespace, retained because the JNI symbols in `native/jni/*.cpp` are named after it
- `org.futo.inputmethodcommon.*` — generic IME settings helper (4 files, in java/src)
- `org.futo.voiceinput.*` — JNI entry points for the Whisper native engine (native/jni)

## File counts (quick)

| Path | Files |
|---|---|
| `java/src` | 369 |
| `tests/src` | 287 |
| `native/jni/src` | 447 |
| `native/dicttoolkit/src` | 23 |
| `common/src` | 14 |
| `java/res/values` | 51 |
| `java/res/layout` | 13 |
| `dictionaries` | 29 |
| `libs` | 7 |

## Build notes to be aware of

- Gradle project with **no `local.properties`** — a build needs your Android SDK path set (or `ANDROID_HOME` in the environment).
- `java/shared.keystore` and `libs/glyph-developer-sdk.jar` are **committed to the repo**; worth knowing if this ever goes public.
- Two product flavors exist: `playstore/` and `stable/`.

## Added in the MyKey rebrand

| Path | What it is |
|:--|:--|
| `java/src/.../uix/fonts/KeyboardFonts.kt` | Keyboard typeface registry — add a font here and it appears in the picker and on the keyboard |
| `java/src/.../uix/settings/pages/Fonts.kt` | Font picker with live typeface previews |
| `java/src/.../uix/theme/presets/{MidnightBlue,Forest,Sunset,Monochrome,RoseQuartz,Terminal}.kt` | Six new theme presets |
| `java/res/font/{spacegrotesk,outfit,jetbrainsmono,notosansbengali}.ttf` | New variable fonts (plus `*_family.xml` in `font/` and `font-v26/`) |
| `branding/BRAND.md` | Name, logo geometry, palette, typography rules |
| `docs/FOLDER-STRUCTURE.md` | This file |
