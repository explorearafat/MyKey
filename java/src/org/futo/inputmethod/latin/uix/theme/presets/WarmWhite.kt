package org.futo.inputmethod.latin.uix.theme.presets

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.ThemeOption
import org.futo.inputmethod.latin.uix.theme.selector.ThemePreview

/*
 * WarmWhite - MyKey theme preset.
 */

private val primaryLight = Color(0xFFB4552D)
private val onPrimaryLight = Color(0xFFFFFFFF)
private val primaryContainerLight = Color(0xFFFFDBCB)
private val onPrimaryContainerLight = Color(0xFF3A1100)
private val secondaryLight = Color(0xFF7D5A44)
private val onSecondaryLight = Color(0xFFFFFFFF)
private val secondaryContainerLight = Color(0xFFFFDBCB)
private val onSecondaryContainerLight = Color(0xFF2C160A)
private val tertiaryLight = Color(0xFF6B5D2E)
private val onTertiaryLight = Color(0xFFFFFFFF)
private val tertiaryContainerLight = Color(0xFFF5E3AE)
private val onTertiaryContainerLight = Color(0xFF211B00)
private val errorLight = Color(0xFFBA1A1A)
private val onErrorLight = Color(0xFFFFFFFF)
private val errorContainerLight = Color(0xFFFFDAD6)
private val onErrorContainerLight = Color(0xFF410002)
private val backgroundLight = Color(0xFFFDF8F3)
private val onBackgroundLight = Color(0xFF211A16)
private val surfaceLight = Color(0xFFFDF8F3)
private val onSurfaceLight = Color(0xFF211A16)
private val surfaceVariantLight = Color(0xFFF0E4D8)
private val onSurfaceVariantLight = Color(0xFF52443A)
private val outlineLight = Color(0xFF84746A)
private val outlineVariantLight = Color(0xFFD6C7BA)
private val scrimLight = Color(0xFF000000)
private val inverseSurfaceLight = Color(0xFF362F2B)
private val inverseOnSurfaceLight = Color(0xFFFBEEE8)
private val inversePrimaryLight = Color(0xFFFFB693)
private val surfaceDimLight = Color(0xFFE1D8D2)
private val surfaceBrightLight = Color(0xFFFDF8F3)
private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
private val surfaceContainerLowLight = Color(0xFFFBF3EC)
private val surfaceContainerLight = Color(0xFFF5EDE6)
private val surfaceContainerHighLight = Color(0xFFEFE7E0)
private val surfaceContainerHighestLight = Color(0xFFE9E1DA)

private val scheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight
)

val WarmWhite = ThemeOption(
    dynamic = false,
    key = "WarmWhite",
    name = R.string.warm_white_theme_name,
    available = { true }
) {
    scheme
}

@Composable
@Preview
private fun PreviewWarmWhite() {
    ThemePreview(WarmWhite)
}
