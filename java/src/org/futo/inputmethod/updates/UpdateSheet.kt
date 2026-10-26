package org.futo.inputmethod.updates

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
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
import org.futo.inputmethod.latin.BuildConfig
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.uix.theme.Doto
import org.futo.inputmethod.latin.uix.theme.Inter
import org.futo.inputmethod.latin.uix.theme.Typography

private val NothingRed = Color(0xFFE5252A)
private val CardBackground = Color(0xFF0E0E11)
private val SurfaceVariant = Color(0xFF1B1B1F)
private val TextSecondary = Color(0xFF8E8E93)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateSheet(
    update: AppUpdate,
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

            // Header row: Title & New Version Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.activity),
                            contentDescription = null,
                            tint = NothingRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "UPDATE AVAILABLE",
                            fontFamily = Doto,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            letterSpacing = 0.5.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Current: v${BuildConfig.VERSION_NAME}",
                            fontFamily = Inter,
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                // Accent version pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(NothingRed.copy(alpha = 0.18f))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "v${update.versionName}",
                        fontFamily = Doto,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = NothingRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Release Notes Card
            Text(
                text = "RELEASE NOTES",
                fontFamily = Doto,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                letterSpacing = 1.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceVariant)
                    .padding(14.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = update.releaseNotes,
                    fontFamily = Inter,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Later button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFF28282C))
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Later",
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }

                // Download & Update button
                Box(
                    modifier = Modifier
                        .weight(1.5f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(NothingRed)
                        .clickable {
                            if (update.downloadUrl.isNotBlank()) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(update.downloadUrl)).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                context.startActivity(intent)
                            }
                            onDismiss()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_down),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Download & Update",
                            fontFamily = Inter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
