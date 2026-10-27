package org.futo.inputmethod.latin.uix.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.futo.inputmethod.latin.BuildConfig
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.DisplayFont
import org.futo.inputmethod.latin.uix.theme.Inter

/*
 * About / credits card.
 *
 * Previously this was a developer business card pinned with hardcoded colours
 * (Color.White text on a fixed #1B1B1F surface) which meant it turned into
 * unreadable white-on-cream under the light Rose Quartz theme. Everything here
 * now comes from MaterialTheme.colorScheme, so it re-skins with the app.
 *
 * It also leads with the product (name, version, build) rather than with a
 * person, since this is the app's About section: attribution to the upstream
 * project and its author belongs here too, but as credit, not as the heading.
 */

@Composable
fun FacebookBrandIcon(modifier: Modifier = Modifier, tint: Color = Color.White) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.65f, h * 0.05f)
            lineTo(w * 0.45f, h * 0.05f)
            cubicTo(w * 0.32f, h * 0.05f, w * 0.28f, h * 0.16f, w * 0.28f, h * 0.28f)
            lineTo(w * 0.28f, h * 0.40f)
            lineTo(w * 0.15f, h * 0.40f)
            lineTo(w * 0.15f, h * 0.58f)
            lineTo(w * 0.28f, h * 0.58f)
            lineTo(w * 0.28f, h * 0.95f)
            lineTo(w * 0.46f, h * 0.95f)
            lineTo(w * 0.46f, h * 0.58f)
            lineTo(w * 0.62f, h * 0.58f)
            lineTo(w * 0.65f, h * 0.40f)
            lineTo(w * 0.46f, h * 0.40f)
            lineTo(w * 0.46f, h * 0.30f)
            cubicTo(w * 0.46f, h * 0.24f, w * 0.49f, h * 0.20f, w * 0.56f, h * 0.20f)
            lineTo(w * 0.65f, h * 0.20f)
            close()
        }
        drawPath(path, color = tint)
    }
}

@Composable
fun XBrandIcon(modifier: Modifier = Modifier, tint: Color = Color.White) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.08f, h * 0.08f)
            lineTo(w * 0.42f, h * 0.54f)
            lineTo(w * 0.08f, h * 0.92f)
            lineTo(w * 0.22f, h * 0.92f)
            lineTo(w * 0.48f, h * 0.62f)
            lineTo(w * 0.73f, h * 0.92f)
            lineTo(w * 0.92f, h * 0.92f)
            lineTo(w * 0.56f, h * 0.44f)
            lineTo(w * 0.88f, h * 0.08f)
            lineTo(w * 0.74f, h * 0.08f)
            lineTo(w * 0.50f, h * 0.35f)
            lineTo(w * 0.27f, h * 0.08f)
            close()
        }
        drawPath(path, color = tint)
    }
}

@Composable
private fun SocialButton(
    label: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) { content() }
}

@Composable
private fun ContactRow(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            fontFamily = Inter,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DeveloperDetailsCard(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val accent = MaterialTheme.colorScheme.primary
    val muted = MaterialTheme.colorScheme.onSurfaceVariant

    fun open(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // ---- Product identity ---------------------------------------------
        NeuCard(modifier = Modifier.fillMaxWidth(), contentPadding = 16.dp) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .border(1.dp, accent.copy(alpha = 0.45f), RoundedCornerShape(13.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "MyKey",
                            fontFamily = DisplayFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Version ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                            fontFamily = Inter,
                            fontSize = 12.sp,
                            color = muted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "A private, offline-first keyboard. Nothing you type leaves your device.",
                    fontFamily = Inter,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = muted
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ---- Developer ----------------------------------------------------
        NeuCard(modifier = Modifier.fillMaxWidth(), contentPadding = 14.dp) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, accent.copy(alpha = 0.40f), RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mykey_avatar),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.size(11.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = DeveloperContact.NAME,
                            fontFamily = DisplayFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = DeveloperContact.ROLE,
                            fontFamily = Inter,
                            fontSize = 11.sp,
                            color = muted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                ContactRow(
                    label = "Instagram",
                    value = DeveloperContact.INSTAGRAM_HANDLE,
                    onClick = { open(DeveloperContact.INSTAGRAM) }
                )
                ContactRow(
                    label = "TikTok",
                    value = DeveloperContact.TIKTOK_HANDLE,
                    onClick = { open(DeveloperContact.TIKTOK) }
                )
                ContactRow(
                    label = "Facebook",
                    value = DeveloperContact.FACEBOOK_HANDLE,
                    onClick = { open(DeveloperContact.FACEBOOK) }
                )
                ContactRow(
                    label = "WhatsApp",
                    value = DeveloperContact.WHATSAPP_DISPLAY,
                    onClick = { open(DeveloperContact.WHATSAPP) }
                )
            }
        }
    }
}
