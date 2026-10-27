package org.futo.inputmethod.latin.uix.theme.presets

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.ThemeOption
import org.futo.inputmethod.latin.uix.theme.selector.ThemePreview

/*
 * RoseQuartz - MyKey theme preset.
 */

private val primaryLight = Color(0xFFC46A8E)
private val onPrimaryLight = Color(0xFFFFFFFF)
private val primaryContainerLight = Color(0xFFF7D2DF)
private val onPrimaryContainerLight = Color(0xFF3B0A21)
private val secondaryLight = Color(0xFF9C5C72)
private val onSecondaryLight = Color(0xFFFFFFFF)
private val secondaryContainerLight = Color(0xFFFBD9E4)
private val onSecondaryContainerLight = Color(0xFF3A0A1D)
private val tertiaryLight = Color(0xFF8A6BA8)
private val onTertiaryLight = Color(0xFFFFFFFF)
private val tertiaryContainerLight = Color(0xFFECDCF7)
private val onTertiaryContainerLight = Color(0xFF2A0F3D)
private val errorLight = Color(0xFFBA1A1A)
private val onErrorLight = Color(0xFFFFFFFF)
private val errorContainerLight = Color(0xFFFFDAD6)
private val onErrorContainerLight = Color(0xFF410002)
private val backgroundLight = Color(0xFFFFF8FA)
private val onBackgroundLight = Color(0xFF1F1216)
private val surfaceLight = Color(0xFFFFF8FA)
private val onSurfaceLight = Color(0xFF1F1216)
private val surfaceVariantLight = Color(0xFFF1DEE4)
private val onSurfaceVariantLight = Color(0xFF52424A)
private val outlineLight = Color(0xFF8A737D)
private val outlineVariantLight = Color(0xFFD9C3CB)
private val scrimLight = Color(0xFF000000)
private val inverseSurfaceLight = Color(0xFF35272C)
private val inverseOnSurfaceLight = Color(0xFFFCEAEF)
private val inversePrimaryLight = Color(0xFFF0B0C8)
private val surfaceDimLight = Color(0xFFE5D6DA)
private val surfaceBrightLight = Color(0xFFFFF8FA)
private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
private val surfaceContainerLowLight = Color(0xFFFEF0F4)
private val surfaceContainerLight = Color(0xFFF9E9EE)
private val surfaceContainerHighLight = Color(0xFFF3E2E8)
private val surfaceContainerHighestLight = Color(0xFFEEDCE3)

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

val RoseQuartz = ThemeOption(
    dynamic = false,
    key = "RoseQuartz",
    name = R.string.rose_quartz_theme_name,
    available = { true }
) {
    scheme
}

@Composable
@Preview
private fun PreviewRoseQuartz() {
    ThemePreview(RoseQuartz)
}
