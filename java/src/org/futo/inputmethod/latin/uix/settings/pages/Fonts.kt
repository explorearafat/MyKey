package org.futo.inputmethod.latin.uix.settings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.fonts.KeyboardFont
import org.futo.inputmethod.latin.uix.KeyboardFontSetting
import org.futo.inputmethod.latin.uix.fonts.KeyboardFonts
import org.futo.inputmethod.latin.uix.settings.NeuCard
import org.futo.inputmethod.latin.uix.settings.ScreenTitle
import org.futo.inputmethod.latin.uix.settings.ScrollableList
import org.futo.inputmethod.latin.uix.settings.SectionHeader
import org.futo.inputmethod.latin.uix.settings.useDataStore
import org.futo.inputmethod.latin.uix.theme.Inter

/*
 * MyKey font picker.
 *
 * A font is a visual choice, not a textual one: the name "JetBrains Mono"
 * tells you very little, seeing it tells you everything. So every row renders
 * its own label AND a sample key row in that face.
 *
 * The samples read from the same registry (KeyboardFonts) the real keyboard
 * uses, so a preview here cannot drift from what actually gets typed.
 */

@Composable
private fun KeySampleRow(font: KeyboardFont, accentText: Boolean) {
    val letters = listOf("q", "w", "e", "r", "t", "y")
    val fg = if (accentText) MaterialTheme.colorScheme.onPrimary
             else MaterialTheme.colorScheme.onSurface
    val bg = if (accentText) MaterialTheme.colorScheme.primary.copy(alpha = 0.22f)
             else MaterialTheme.colorScheme.surfaceVariant

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        letters.forEach { ch ->
            Box(
                modifier = Modifier
                    .size(width = 26.dp, height = 30.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(bg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = ch,
                    fontFamily = font.family,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = fg
                )
            }
        }
    }
}

@Composable
private fun FontRow(
    font: KeyboardFont,
    selected: Boolean,
    onClick: () -> Unit
) {
    val accent = MaterialTheme.colorScheme.primary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (selected) accent.copy(alpha = 0.10f)
                else MaterialTheme.colorScheme.surface
            )
            .border(
                width = 1.dp,
                color = if (selected) accent.copy(alpha = 0.55f)
                        else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = font.label,
                fontFamily = font.family,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = font.description,
                fontFamily = Inter,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            KeySampleRow(font = font, accentText = selected)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (selected) accent else MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(11.dp)
                        .clip(CircleShape)
                        .background(accent)
                )
            }
        }
    }
}

@Composable
private fun PreviewPanel(font: KeyboardFont) {
    NeuCard(modifier = Modifier.fillMaxWidth(), contentPadding = 16.dp) {
        Column {
            Text(
                text = "PREVIEW",
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 1.2.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "The quick brown fox",
                fontFamily = font.family,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                lineHeight = 30.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "jumps over the lazy dog 0123456789",
                fontFamily = font.family,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            KeySampleRow(font = font, accentText = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FontsScreen(navController: NavHostController = rememberNavController()) {
    val (storedFont, setFont) = useDataStore(
        key = KeyboardFontSetting.key,
        default = KeyboardFontSetting.default
    )

    // Local mirror so the preview updates the instant a row is tapped, without
    // waiting for the DataStore round-trip to come back.
    var selectedId by remember(storedFont) { mutableStateOf(storedFont) }

    val selected = KeyboardFonts.firstOrNull { it.id == selectedId } ?: KeyboardFonts.first()

    ScrollableList {
        ScreenTitle("Fonts", showBack = true, navController)

        PreviewPanel(font = selected)

        SectionHeader("Keyboard font")

        Text(
            text = "Applies to the keys, key hints, popups and the suggestion bar. " +
                    "Bengali text always renders in the bundled Bengali face so it stays legible.",
            fontFamily = Inter,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        KeyboardFonts.forEach { font ->
            FontRow(
                font = font,
                selected = font.id == selectedId,
                onClick = {
                    selectedId = font.id
                    setFont(font.id)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                .padding(12.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.help_circle),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "This setting changes the typeface only, not the key size. " +
                        "Use Resize Keyboard to change how tall the keys are.",
                fontFamily = Inter,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
