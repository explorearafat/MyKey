package org.futo.inputmethod.latin.uix.theme.presets

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.ThemeOption
import org.futo.inputmethod.latin.uix.theme.selector.ThemePreview

/*
 * Terminal - MyKey theme preset.
 */

private val primaryDark = Color(0xFF3DFF9E)
private val onPrimaryDark = Color(0xFF00210F)
private val primaryContainerDark = Color(0xFF004D25)
private val onPrimaryContainerDark = Color(0xFFA8FFC9)
private val secondaryDark = Color(0xFF7CF5C0)
private val onSecondaryDark = Color(0xFF00341C)
private val secondaryContainerDark = Color(0xFF00522E)
private val onSecondaryContainerDark = Color(0xFFB8FFD9)
private val tertiaryDark = Color(0xFFE8FF6B)
private val onTertiaryDark = Color(0xFF263000)
private val tertiaryContainerDark = Color(0xFF3A4A00)
private val onTertiaryContainerDark = Color(0xFFF0FFB0)
private val errorDark = Color(0xFFFF6B6B)
private val onErrorDark = Color(0xFF4A0000)
private val errorContainerDark = Color(0xFF7A0F0F)
private val onErrorContainerDark = Color(0xFFFFD6D6)
private val backgroundDark = Color(0xFF040A06)
private val onBackgroundDark = Color(0xFFC8FFE0)
private val surfaceDark = Color(0xFF071009)
private val onSurfaceDark = Color(0xFFC8FFE0)
private val surfaceVariantDark = Color(0xFF0F2318)
private val onSurfaceVariantDark = Color(0xFF8FD9AE)
private val outlineDark = Color(0xFF1E4A31)
private val outlineVariantDark = Color(0xFF123322)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFC8FFE0)
private val inverseOnSurfaceDark = Color(0xFF0F2318)
private val inversePrimaryDark = Color(0xFF00A85A)
private val surfaceDimDark = Color(0xFF040A06)
private val surfaceBrightDark = Color(0xFF14351F)
private val surfaceContainerLowestDark = Color(0xFF020604)
private val surfaceContainerLowDark = Color(0xFF071009)
private val surfaceContainerDark = Color(0xFF0A150D)
private val surfaceContainerHighDark = Color(0xFF0F2318)
private val surfaceContainerHighestDark = Color(0xFF173426)

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

val Terminal = ThemeOption(
    dynamic = false,
    key = "Terminal",
    name = R.string.terminal_theme_name,
    available = { true }
) {
    scheme
}

@Composable
@Preview
private fun PreviewTerminal() {
    ThemePreview(Terminal)
}
