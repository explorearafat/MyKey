package org.futo.inputmethod.latin.uix.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.futo.inputmethod.latin.R

/*
 * MyKey type system.
 *
 * Every family here is a VARIABLE font, so a single .ttf can serve several
 * weights by pinning the "wght" axis via FontVariation.Settings. That keeps
 * the APK small: 7 files cover 4 families x multiple weights instead of one
 * file per weight.
 *
 * Families:
 *   Doto            - dot-matrix display face, used for headings and labels
 *   Inter           - the workhorse UI text face
 *   SpaceGrotesk    - geometric display face, MyKey default for headings
 *   Outfit          - friendlier geometric face for large titles
 *   JetBrainsMono   - monospace, for code/debug style surfaces
 *   NotoSansBengali - Bengali script coverage for the Bengali layouts
 */

@OptIn(ExperimentalTextApi::class)
private fun variable(resId: Int, weight: Int) = Font(
    resId = resId,
    weight = FontWeight(weight),
    variationSettings = FontVariation.Settings(FontVariation.weight(weight))
)

@OptIn(ExperimentalTextApi::class)
val Doto = FontFamily(
    variable(R.font.doto, 700),
    variable(R.font.doto, 900),
)

@OptIn(ExperimentalTextApi::class)
val Inter = FontFamily(
    variable(R.font.inter, 400),
    variable(R.font.inter, 500),
    variable(R.font.inter, 600),
    variable(R.font.inter, 700),
)

@OptIn(ExperimentalTextApi::class)
val SpaceGrotesk = FontFamily(
    variable(R.font.spacegrotesk, 300),
    variable(R.font.spacegrotesk, 400),
    variable(R.font.spacegrotesk, 500),
    variable(R.font.spacegrotesk, 600),
    variable(R.font.spacegrotesk, 700),
)

@OptIn(ExperimentalTextApi::class)
val Outfit = FontFamily(
    variable(R.font.outfit, 300),
    variable(R.font.outfit, 400),
    variable(R.font.outfit, 500),
    variable(R.font.outfit, 600),
    variable(R.font.outfit, 700),
    variable(R.font.outfit, 800),
)

@OptIn(ExperimentalTextApi::class)
val JetBrainsMono = FontFamily(
    variable(R.font.jetbrainsmono, 400),
    variable(R.font.jetbrainsmono, 500),
    variable(R.font.jetbrainsmono, 700),
)

@OptIn(ExperimentalTextApi::class)
val NotoSansBengali = FontFamily(
    variable(R.font.notosansbengali, 400),
    variable(R.font.notosansbengali, 500),
    variable(R.font.notosansbengali, 600),
    variable(R.font.notosansbengali, 700),
)

/**
 * The family used for brand headings and section labels across MyKey.
 * Changing this one value re-types the whole app chrome.
 */
val DisplayFont = SpaceGrotesk

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = DisplayFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.2.sp
    ),
    titleMedium = TextStyle(
        fontFamily = DisplayFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.2.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.1.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = DisplayFont,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.8.sp
    )
)
