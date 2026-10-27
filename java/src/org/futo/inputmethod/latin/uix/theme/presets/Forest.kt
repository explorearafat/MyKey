package org.futo.inputmethod.latin.uix.theme.presets

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.ThemeOption
import org.futo.inputmethod.latin.uix.theme.selector.ThemePreview

/*
 * Forest - MyKey theme preset.
 */

private val primaryDark = Color(0xFF7BD88F)
private val onPrimaryDark = Color(0xFF00391B)
private val primaryContainerDark = Color(0xFF0F5132)
private val onPrimaryContainerDark = Color(0xFFB7F5C8)
private val secondaryDark = Color(0xFFA8C5A0)
private val onSecondaryDark = Color(0xFF12331A)
private val secondaryContainerDark = Color(0xFF234A2B)
private val onSecondaryContainerDark = Color(0xFFC4E8B9)
private val tertiaryDark = Color(0xFFE8C468)
private val onTertiaryDark = Color(0xFF3A2C00)
private val tertiaryContainerDark = Color(0xFF54410A)
private val onTertiaryContainerDark = Color(0xFFFFE8A8)
private val errorDark = Color(0xFFFFB4AB)
private val onErrorDark = Color(0xFF690005)
private val errorContainerDark = Color(0xFF93000A)
private val onErrorContainerDark = Color(0xFFFFDAD6)
private val backgroundDark = Color(0xFF0B1410)
private val onBackgroundDark = Color(0xFFDDE7DC)
private val surfaceDark = Color(0xFF0F1913)
private val onSurfaceDark = Color(0xFFDDE7DC)
private val surfaceVariantDark = Color(0xFF1E2C22)
private val onSurfaceVariantDark = Color(0xFFB2C4B0)
private val outlineDark = Color(0xFF405441)
private val outlineVariantDark = Color(0xFF2B3D2E)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFDDE7DC)
private val inverseOnSurfaceDark = Color(0xFF243027)
private val inversePrimaryDark = Color(0xFF2E6B3F)
private val surfaceDimDark = Color(0xFF0B1410)
private val surfaceBrightDark = Color(0xFF2F4034)
private val surfaceContainerLowestDark = Color(0xFF070F0B)
private val surfaceContainerLowDark = Color(0xFF101B14)
private val surfaceContainerDark = Color(0xFF142017)
private val surfaceContainerHighDark = Color(0xFF1C2C21)
private val surfaceContainerHighestDark = Color(0xFF26382B)

private val scheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark
)

val Forest = ThemeOption(
    dynamic = false,
    key = "Forest",
    name = R.string.forest_theme_name,
    available = { true }
) {
    scheme
}

@Composable
@Preview
private fun PreviewForest() {
    ThemePreview(Forest)
}
