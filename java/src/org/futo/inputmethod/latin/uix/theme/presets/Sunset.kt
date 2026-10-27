package org.futo.inputmethod.latin.uix.theme.presets

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.ThemeOption
import org.futo.inputmethod.latin.uix.theme.selector.ThemePreview

/*
 * Sunset - MyKey theme preset.
 */

private val primaryDark = Color(0xFFFF8A5B)
private val onPrimaryDark = Color(0xFF4A1600)
private val primaryContainerDark = Color(0xFF7A2E12)
private val onPrimaryContainerDark = Color(0xFFFFDBCC)
private val secondaryDark = Color(0xFFFFB98A)
private val onSecondaryDark = Color(0xFF4A2000)
private val secondaryContainerDark = Color(0xFF6B3A1C)
private val onSecondaryContainerDark = Color(0xFFFFE0CC)
private val tertiaryDark = Color(0xFFFFD166)
private val onTertiaryDark = Color(0xFF3D2E00)
private val tertiaryContainerDark = Color(0xFF5A4400)
private val onTertiaryContainerDark = Color(0xFFFFEAA8)
private val errorDark = Color(0xFFFFB4AB)
private val onErrorDark = Color(0xFF690005)
private val errorContainerDark = Color(0xFF93000A)
private val onErrorContainerDark = Color(0xFFFFDAD6)
private val backgroundDark = Color(0xFF1A0E0A)
private val onBackgroundDark = Color(0xFFF7E2D8)
private val surfaceDark = Color(0xFF22120C)
private val onSurfaceDark = Color(0xFFF7E2D8)
private val surfaceVariantDark = Color(0xFF3A2118)
private val onSurfaceVariantDark = Color(0xFFD8B7A6)
private val outlineDark = Color(0xFF6B4433)
private val outlineVariantDark = Color(0xFF4A2E22)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFF7E2D8)
private val inverseOnSurfaceDark = Color(0xFF3A241A)
private val inversePrimaryDark = Color(0xFFB34A22)
private val surfaceDimDark = Color(0xFF1A0E0A)
private val surfaceBrightDark = Color(0xFF503227)
private val surfaceContainerLowestDark = Color(0xFF120805)
private val surfaceContainerLowDark = Color(0xFF22120C)
private val surfaceContainerDark = Color(0xFF2A1710)
private val surfaceContainerHighDark = Color(0xFF38211A)
private val surfaceContainerHighestDark = Color(0xFF472C23)

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

val Sunset = ThemeOption(
    dynamic = false,
    key = "Sunset",
    name = R.string.sunset_theme_name,
    available = { true }
) {
    scheme
}

@Composable
@Preview
private fun PreviewSunset() {
    ThemePreview(Sunset)
}
