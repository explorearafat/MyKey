package org.futo.inputmethod.latin.uix.theme.presets

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.ThemeOption
import org.futo.inputmethod.latin.uix.theme.selector.ThemePreview

/*
 * MidnightBlue - MyKey theme preset.
 */

private val primaryDark = Color(0xFFFF7A45)
private val onPrimaryDark = Color(0xFF0B1220)
private val primaryContainerDark = Color(0xFF1B2A44)
private val onPrimaryContainerDark = Color(0xFFDCE7FF)
private val secondaryDark = Color(0xFF7FA6E8)
private val onSecondaryDark = Color(0xFF0B1220)
private val secondaryContainerDark = Color(0xFF17243A)
private val onSecondaryContainerDark = Color(0xFFDCE7FF)
private val tertiaryDark = Color(0xFFF2B233)
private val onTertiaryDark = Color(0xFF231A00)
private val tertiaryContainerDark = Color(0xFF3A2C08)
private val onTertiaryContainerDark = Color(0xFFFFE7AE)
private val errorDark = Color(0xFFFFB4AB)
private val onErrorDark = Color(0xFF690005)
private val errorContainerDark = Color(0xFF93000A)
private val onErrorContainerDark = Color(0xFFFFDAD6)
private val backgroundDark = Color(0xFF070D18)
private val onBackgroundDark = Color(0xFFE2E8F4)
private val surfaceDark = Color(0xFF0B1220)
private val onSurfaceDark = Color(0xFFE2E8F4)
private val surfaceVariantDark = Color(0xFF17243A)
private val onSurfaceVariantDark = Color(0xFFA9B8D0)
private val outlineDark = Color(0xFF31425E)
private val outlineVariantDark = Color(0xFF22304A)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFE2E8F4)
private val inverseOnSurfaceDark = Color(0xFF1B2436)
private val inversePrimaryDark = Color(0xFF005BC0)
private val surfaceDimDark = Color(0xFF070D18)
private val surfaceBrightDark = Color(0xFF2A3A55)
private val surfaceContainerLowestDark = Color(0xFF04080F)
private val surfaceContainerLowDark = Color(0xFF0C1524)
private val surfaceContainerDark = Color(0xFF101B2D)
private val surfaceContainerHighDark = Color(0xFF182640)
private val surfaceContainerHighestDark = Color(0xFF213152)

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

val MidnightBlue = ThemeOption(
    dynamic = false,
    key = "MidnightBlue",
    name = R.string.midnight_blue_theme_name,
    available = { true }
) {
    scheme
}

@Composable
@Preview
private fun PreviewMidnightBlue() {
    ThemePreview(MidnightBlue)
}
