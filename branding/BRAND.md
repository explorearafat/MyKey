# MyKey brand reference

## Name
**MyKey** — one word, capital M and K. Never "My Key" or "mykey" in UI copy.
The lowercase form `mykey` is only used for package ids, URLs and repo names.

## Tagline
> Your keyboard, your way

## Logo
A rounded keycap containing a bold **M**, with an amber accent dot standing in
for the key in MyKey.

| Colour | Hex | Role |
|:--|:--|:--|
| Ink Navy | `#101828` | Keycap body, icon background |
| Signal Blue | `#4C8DF6` | The M glyph |
| Amber | `#F2B233` | Keycap outline and accent dot |

### Icon files

| Path | Purpose |
|:--|:--|
| `java/res/drawable/ic_launcher_foreground.xml` | Adaptive icon foreground (108dp safe zone) |
| `java/res/drawable/mykey_logo.xml` | In-app logo mark |
| `java/res/values/ic_launcher_background.xml` | Adaptive icon background colour |
| `java/res/mipmap-*/ic_launcher.webp` | Legacy launcher icons, 5 densities |
| `java/res/mipmap-*/ic_launcher_round.webp` | Round variants, 5 densities |
| `java/ic_launcher-playstore.png` | 512px Play Store listing icon |
| `branding/icon-contact-sheet.png` | Visual QA sheet |

The vector and the raster icons share the same geometry, so both icon systems
render identically on any Android version.

## Typography

| Family | Role | Source |
|:--|:--|:--|
| Space Grotesk | App headings, section labels (`DisplayFont`) | OFL |
| Inter | Interface body text | OFL |
| Outfit | Selectable keyboard face | OFL |
| JetBrains Mono | Selectable keyboard face | OFL |
| Doto | Selectable keyboard face | OFL |
| Ndot | Signature dot-matrix keyboard face | see NOTICE |
| Noto Sans Bengali | Bengali script coverage | OFL |

All are variable fonts, so one `.ttf` covers multiple weights via the `wght`
axis. Changing `DisplayFont` in `uix/theme/Type.kt` re-types the whole app.

## Writing style
- Sentence case for headings and buttons.
- Plain language over jargon: "Keyboard font", not "Typeface override".
- Describe outcomes, not mechanisms: "Nothing you type leaves your device".
