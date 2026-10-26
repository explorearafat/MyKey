package org.futo.inputmethod.announcements

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.Doto
import org.futo.inputmethod.latin.uix.theme.Inter

private val NothingRed = Color(0xFFE5252A)
private val CardBackground = Color(0xFF0E0E11)
private val SurfaceVariant = Color(0xFF1B1B1F)
private val BorderColor = Color(0xFF26262B)
private val TextSecondary = Color(0xFF8E8E93)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementSheet(
    announcement: Announcement,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = CardBackground,
        scrimColor = Color.Black.copy(alpha = 0.65f),
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            // Centered Nothing OS pill drag handle
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(36.dp)
                    .height(4.dp)
                    .background(Color(0xFF48484B), RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Header row: Icon, Title, and Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(if (announcement.isCritical) NothingRed.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.1f))
                            .border(1.dp, if (announcement.isCritical) NothingRed.copy(alpha = 0.4f) else Color.White.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_campaign),
                            contentDescription = null,
                            tint = if (announcement.isCritical) NothingRed else Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column {
                        Text(
                            text = announcement.title.uppercase(),
                            fontFamily = Doto,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            letterSpacing = 0.5.sp,
                            color = Color.White
                        )
                        if (announcement.date.isNotBlank()) {
                            Text(
                                text = announcement.date,
                                fontFamily = Inter,
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }

                // Badge pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (announcement.isCritical) NothingRed else NothingRed.copy(alpha = 0.18f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (announcement.isCritical) "CRITICAL" else announcement.badge.uppercase(),
                        fontFamily = Doto,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp,
                        color = if (announcement.isCritical) Color.White else NothingRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content body in scrollable container
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (announcement.displaySummary.isNotBlank()) {
                    Text(
                        text = announcement.displaySummary,
                        fontFamily = Inter,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFFDDDDDD)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Highlights list if present
                if (announcement.highlights.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceVariant)
                            .border(1.dp, BorderColor, RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            announcement.highlights.forEach { highlight ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(top = 6.dp)
                                            .size(5.dp)
                                            .clip(CircleShape)
                                            .background(NothingRed)
                                    )
                                    Text(
                                        text = highlight,
                                        fontFamily = Inter,
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action button(s)
            val actionLabel = announcement.displayActionLabel
            val actionUrl = announcement.actionUrl

            if (!actionLabel.isNullOrBlank() && !actionUrl.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(NothingRed)
                        .clickable {
                            runCatching {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(actionUrl)).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                context.startActivity(intent)
                            }
                            onDismiss()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = actionLabel,
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Dismiss capsule
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(SurfaceVariant)
                    .border(1.dp, BorderColor, RoundedCornerShape(22.dp))
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Close",
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }
        }
    }
}
