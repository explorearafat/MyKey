package org.futo.inputmethod.latin.uix.fonts

import android.content.Context
import android.graphics.Typeface
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.res.ResourcesCompat
import org.futo.inputmethod.latin.R

/*
 * MyKey keyboard typography registry.
 *
 * Before this file the keyboard font was a boolean in disguise: the setting
 * held either "nothing" or "normal" and every call site did its own
 * `if (font == "normal") Typeface.DEFAULT else ndot` branch. Adding a third
 * font meant touching BasicThemeProvider, ActionBar and the settings UI.
 *
 * Now there is ONE list. A font is added here and it automatically shows up
 * in the picker, on the keyboard, and in the suggestion bar.
 *
 * Each entry needs two things because the keyboard draws text two ways:
 *   - `family`   : Compose FontFamily, used by the suggestion bar / popups
 *   - `typeface` : android.graphics.Typeface, used by the canvas key renderer
 * `typeface` is resolved lazily at Context-call time, since it needs a Context
 * and we do not want to hold one in a top-level val.
 *
 * The stored setting value is the `id` string. "nothing" is kept as the id for
 * the dot-matrix face so existing installs keep their current look after the
 * rebrand instead of silently resetting to the default.
 */

data class KeyboardFont(
    val id: String,
    val label: String,
    val description: String,
    val family: FontFamily,
    val fontRes: Int? // null means the platform default typeface
) {
    fun typeface(context: Context): Typeface =
        fontRes?.let { ResourcesCompat.getFont(context, it) } ?: Typeface.DEFAULT
}

val KeyboardFonts: List<KeyboardFont> = listOf(
    KeyboardFont(
        id = "nothing",
        label = "Dot Matrix",
        description = "The signature dotted face MyKey ships with",
        family = FontFamily(Font(R.font.ndot_family)),
        fontRes = R.font.ndot_family
    ),
    KeyboardFont(
        id = "normal",
        label = "System",
        description = "Whatever your device uses by default",
        family = FontFamily.Default,
        fontRes = null
    ),
    KeyboardFont(
        id = "spacegrotesk",
        label = "Space Grotesk",
        description = "Geometric, technical, a little retro-futurist",
        family = FontFamily(Font(R.font.spacegrotesk)),
        fontRes = R.font.spacegrotesk
    ),
    KeyboardFont(
        id = "outfit",
        label = "Outfit",
        description = "Soft geometric shapes, friendly and round",
        family = FontFamily(Font(R.font.outfit)),
        fontRes = R.font.outfit
    ),
    KeyboardFont(
        id = "inter",
        label = "Inter",
        description = "Neutral and extremely legible at small sizes",
        family = FontFamily(Font(R.font.inter)),
        fontRes = R.font.inter
    ),
    KeyboardFont(
        id = "jetbrainsmono",
        label = "JetBrains Mono",
        description = "Monospaced, for people who like their keys typewritten",
        family = FontFamily(Font(R.font.jetbrainsmono)),
        fontRes = R.font.jetbrainsmono
    ),
    KeyboardFont(
        id = "doto",
        label = "Doto",
        description = "Round dot display face, heavier than Dot Matrix",
        family = FontFamily(Font(R.font.doto)),
        fontRes = R.font.doto
    )
)

/** The font id used when the stored setting is missing or unrecognised. */
const val DEFAULT_KEYBOARD_FONT_ID = "nothing"

val KeyboardFontIds: List<String> = KeyboardFonts.map { it.id }
val KeyboardFontLabels: List<String> = KeyboardFonts.map { it.label }

/** Look up a font by its stored id, falling back to the default. */
fun keyboardFontById(id: String): KeyboardFont =
    KeyboardFonts.firstOrNull { it.id == id }
        ?: KeyboardFonts.first { it.id == DEFAULT_KEYBOARD_FONT_ID }
