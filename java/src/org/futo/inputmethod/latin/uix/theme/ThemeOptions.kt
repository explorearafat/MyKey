package org.futo.inputmethod.latin.uix.theme

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material3.ColorScheme
import org.futo.inputmethod.latin.uix.theme.presets.AMOLEDDarkPurple
import org.futo.inputmethod.latin.uix.theme.presets.AMOLEDNothing
import org.futo.inputmethod.latin.uix.theme.presets.WarmWhite
import org.futo.inputmethod.latin.uix.theme.presets.ClassicBlack
import org.futo.inputmethod.latin.uix.theme.presets.OrbitTheme
import org.futo.inputmethod.latin.uix.theme.presets.NothingLight
import org.futo.inputmethod.latin.uix.theme.presets.NothingDark
import org.futo.inputmethod.latin.uix.theme.presets.ClassicMaterialDark
import org.futo.inputmethod.latin.uix.theme.presets.ClassicMaterialLight
import org.futo.inputmethod.latin.uix.theme.presets.CottonCandy
import org.futo.inputmethod.latin.uix.theme.presets.DeepSeaDark
import org.futo.inputmethod.latin.uix.theme.presets.DeepSeaLight
import org.futo.inputmethod.latin.uix.theme.presets.DynamicDarkTheme
import org.futo.inputmethod.latin.uix.theme.presets.DynamicLightTheme
import org.futo.inputmethod.latin.uix.theme.presets.DynamicNothing
import org.futo.inputmethod.latin.uix.theme.presets.DynamicSystemTheme
import org.futo.inputmethod.latin.uix.theme.presets.Emerald
import org.futo.inputmethod.latin.uix.theme.presets.Snowfall
import org.futo.inputmethod.latin.uix.theme.presets.SteelGray
import org.futo.inputmethod.latin.uix.theme.presets.Sunflower
import org.futo.inputmethod.latin.uix.theme.presets.VoiceInputTheme
import org.futo.inputmethod.latin.uix.theme.presets.MidnightBlue
import org.futo.inputmethod.latin.uix.theme.presets.Forest
import org.futo.inputmethod.latin.uix.theme.presets.Sunset
import org.futo.inputmethod.latin.uix.theme.presets.Monochrome
import org.futo.inputmethod.latin.uix.theme.presets.RoseQuartz
import org.futo.inputmethod.latin.uix.theme.presets.Terminal

data class ThemeOption(
    val dynamic: Boolean,
    val key: String,
    @StringRes val name: Int,
    val available: (Context) -> Boolean,
    val obtainColors: (Context) -> ColorScheme,
)

val ThemeOptions = mapOf(
    // MyKey defaults come first so they lead the picker
    WarmWhite.key to WarmWhite,
    ClassicBlack.key to ClassicBlack,

    OrbitTheme.key to OrbitTheme,
    VoiceInputTheme.key to VoiceInputTheme,

    DynamicNothing.key to DynamicNothing,
    DynamicSystemTheme.key to DynamicSystemTheme,
    DynamicDarkTheme.key to DynamicDarkTheme,
    DynamicLightTheme.key to DynamicLightTheme,

    ClassicMaterialDark.key to ClassicMaterialDark,
    ClassicMaterialLight.key to ClassicMaterialLight,
    AMOLEDDarkPurple.key to AMOLEDDarkPurple,
    AMOLEDNothing.key to AMOLEDNothing,
    NothingLight.key to NothingLight,
    NothingDark.key to NothingDark,

    Sunflower.key to Sunflower,
    Snowfall.key to Snowfall,
    SteelGray.key to SteelGray,
    Emerald.key to Emerald,
    CottonCandy.key to CottonCandy,

    DeepSeaLight.key to DeepSeaLight,
    DeepSeaDark.key to DeepSeaDark,

    // MyKey additions
    MidnightBlue.key to MidnightBlue,
    Forest.key to Forest,
    Sunset.key to Sunset,
    Monochrome.key to Monochrome,
    RoseQuartz.key to RoseQuartz,
    Terminal.key to Terminal,

)

val ThemeOptionKeys = ThemeOptions.keys
