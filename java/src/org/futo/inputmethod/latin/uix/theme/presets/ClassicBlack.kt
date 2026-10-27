package org.futo.inputmethod.latin.uix.theme.presets

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.ThemeOption
import org.futo.inputmethod.latin.uix.theme.selector.ThemePreview

/*
 * ClassicBlack - MyKey theme preset.
 */

private val primaryDark = Color(0xFFE8E8EA)
private val onPrimaryDark = Color(0xFF121214)
private val primaryContainerDark = Color(0xFF2A2A2E)
private val onPrimaryContainerDark = Color(0xFFECECEF)
private val secondaryDark = Color(0xFFA9A9B2)
private val onSecondaryDark = Color(0xFF121214)
private val secondaryContainerDark = Color(0xFF232326)
private val onSecondaryContainerDark = Color(0xFFE4E4E8)
private val tertiaryDark = Color(0xFFC9A227)
private val onTertiaryDark = Color(0xFF1F1700)
private val tertiaryContainerDark = Color(0xFF3A2F00)
private val onTertiaryContainerDark = Color(0xFFF7E7A8)
private val errorDark = Color(0xFFFFB4AB)
private val onErrorDark = Color(0xFF690005)
private val errorContainerDark = Color(0xFF93000A)
private val onErrorContainerDark = Color(0xFFFFDAD6)
private val backgroundDark = Color(0xFF000000)
private val onBackgroundDark = Color(0xFFE9E9EC)
private val surfaceDark = Color(0xFF101012)
private val onSurfaceDark = Color(0xFFE9E9EC)
private val surfaceVariantDark = Color(0xFF202024)
private val onSurfaceVariantDark = Color(0xFFADADB6)
private val outlineDark = Color(0xFF3A3A40)
private val outlineVariantDark = Color(0xFF282830)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFE9E9EC)
private val inverseOnSurfaceDark = Color(0xFF1C1C20)
private val inversePrimaryDark = Color(0xFF5C5C66)
private val surfaceDimDark = Color(0xFF000000)
private val surfaceBrightDark = Color(0xFF2E2E34)
private val surfaceContainerLowestDark = Color(0xFF000000)
private val surfaceContainerLowDark = Color(0xFF0A0A0C)
private val surfaceContainerDark = Color(0xFF141417)
private val surfaceContainerHighDark = Color(0xFF1E1E22)
private val surfaceContainerHighestDark = Color(0xFF282830)

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

val ClassicBlack = ThemeOption(
    dynamic = false,
    key = "ClassicBlack",
    name = R.string.classic_black_theme_name,
    available = { true }
) {
    scheme
}

@Composable
@Preview
private fun PreviewClassicBlack() {
    ThemePreview(ClassicBlack)
}
