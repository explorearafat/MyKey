package org.futo.inputmethod.latin.uix.theme.presets

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.ThemeOption
import org.futo.inputmethod.latin.uix.theme.selector.ThemePreview

/*
 * Monochrome - MyKey theme preset.
 */

private val primaryDark = Color(0xFFEDEDED)
private val onPrimaryDark = Color(0xFF1A1A1A)
private val primaryContainerDark = Color(0xFF2E2E2E)
private val onPrimaryContainerDark = Color(0xFFF5F5F5)
private val secondaryDark = Color(0xFFB8B8B8)
private val onSecondaryDark = Color(0xFF1A1A1A)
private val secondaryContainerDark = Color(0xFF262626)
private val onSecondaryContainerDark = Color(0xFFEDEDED)
private val tertiaryDark = Color(0xFFD4D4D4)
private val onTertiaryDark = Color(0xFF1A1A1A)
private val tertiaryContainerDark = Color(0xFF303030)
private val onTertiaryContainerDark = Color(0xFFF0F0F0)
private val errorDark = Color(0xFFFFB4AB)
private val onErrorDark = Color(0xFF690005)
private val errorContainerDark = Color(0xFF93000A)
private val onErrorContainerDark = Color(0xFFFFDAD6)
private val backgroundDark = Color(0xFF0A0A0A)
private val onBackgroundDark = Color(0xFFEDEDED)
private val surfaceDark = Color(0xFF121212)
private val onSurfaceDark = Color(0xFFEDEDED)
private val surfaceVariantDark = Color(0xFF222222)
private val onSurfaceVariantDark = Color(0xFFB0B0B0)
private val outlineDark = Color(0xFF3D3D3D)
private val outlineVariantDark = Color(0xFF2A2A2A)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFEDEDED)
private val inverseOnSurfaceDark = Color(0xFF262626)
private val inversePrimaryDark = Color(0xFF5C5C5C)
private val surfaceDimDark = Color(0xFF0A0A0A)
private val surfaceBrightDark = Color(0xFF3A3A3A)
private val surfaceContainerLowestDark = Color(0xFF050505)
private val surfaceContainerLowDark = Color(0xFF101010)
private val surfaceContainerDark = Color(0xFF161616)
private val surfaceContainerHighDark = Color(0xFF202020)
private val surfaceContainerHighestDark = Color(0xFF2B2B2B)

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

val Monochrome = ThemeOption(
    dynamic = false,
    key = "Monochrome",
    name = R.string.monochrome_theme_name,
    available = { true }
) {
    scheme
}

@Composable
@Preview
private fun PreviewMonochrome() {
    ThemePreview(Monochrome)
}
