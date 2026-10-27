package org.futo.inputmethod.latin.uix.settings.pages

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.futo.inputmethod.announcements.Announcement
import org.futo.inputmethod.announcements.AnnouncementManager
import org.futo.inputmethod.announcements.AnnouncementSheet
import org.futo.inputmethod.latin.ActiveSubtype
import org.futo.inputmethod.latin.BuildConfig
import org.futo.inputmethod.latin.R
import org.futo.inputmethod.latin.Subtypes
import org.futo.inputmethod.latin.SubtypesSetting
import org.futo.inputmethod.latin.uix.TextEditPopupActivity
import org.futo.inputmethod.latin.uix.getSettingBlocking
import org.futo.inputmethod.latin.uix.setSettingBlocking
import org.futo.inputmethod.latin.uix.settings.DeveloperDetailsCard
import org.futo.inputmethod.latin.uix.settings.NavigationItem
import org.futo.inputmethod.latin.uix.settings.NavigationItemStyle
import org.futo.inputmethod.latin.uix.settings.NeuCard
import org.futo.inputmethod.latin.uix.settings.SectionHeader
import org.futo.inputmethod.latin.uix.settings.useDataStoreValue
import org.futo.inputmethod.latin.uix.theme.DisplayFont
import org.futo.inputmethod.latin.uix.theme.Inter
import org.futo.inputmethod.updates.AppUpdate
import org.futo.inputmethod.updates.UpdateChecker
import org.futo.inputmethod.updates.UpdateSheet

/*
 * MyKey home screen.
 *
 * Layout, top to bottom:
 *   1. BrandHero      - logo mark, wordmark, tagline, version pill
 *   2. QuickActionGrid- the four things people actually come here to do
 *   3. Input & Languages
 *   4. Bengali Layouts
 *   5. Customization
 *   6. Updates
 *   7. About Developer
 *   8. A pinned "Try typing" capsule that stays put while the page scrolls.
 *
 * All colours are pulled from MaterialTheme.colorScheme so the whole screen
 * re-skins itself whenever the user changes keyboard theme.
 */

@Composable
private fun BrandHero(
    versionName: String,
    modifier: Modifier = Modifier
) {
    val accent = MaterialTheme.colorScheme.primary
    val onSurface = MaterialTheme.colorScheme.onSurface
    val muted = MaterialTheme.colorScheme.onSurfaceVariant

    NeuCard(modifier = modifier.fillMaxWidth(), contentPadding = 18.dp) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(1.dp, accent.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "MyKey",
                    fontFamily = DisplayFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    letterSpacing = 1.sp,
                    color = onSurface
                )
                Text(
                    text = "Your keyboard, your way",
                    fontFamily = Inter,
                    fontSize = 12.sp,
                    color = muted
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(accent.copy(alpha = 0.14f))
                    .border(1.dp, accent.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "v$versionName",
                    fontFamily = DisplayFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = accent
                )
            }
        }
    }
}

@Composable
private fun QuickActionTile(
    title: String,
    iconRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val accent = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(19.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun BengaliLayoutToggleRow(
    title: String,
    subtitle: String,
    active: Boolean,
    canToggle: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontFamily = Inter,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = active,
            enabled = canToggle,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}

@Composable
private fun Divider() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val isDeveloper = useDataStoreValue(IS_DEVELOPER)
    val isCheckingUpdate by UpdateChecker.isChecking.collectAsState()
    var showUpdateSheet by remember { mutableStateOf(false) }
    var availableUpdate by remember { mutableStateOf<AppUpdate?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Announcements
    val announcementManager = remember { AnnouncementManager.getInstance(context) }
    val availableAnnouncement by announcementManager.availableAnnouncement.collectAsState()
    var showAnnouncementSheet by remember { mutableStateOf(false) }

    // Bengali subtypes
    val currentSubtypes = useDataStoreValue(SubtypesSetting)
    val avroSubtype = "bn_BD:KeyboardLayoutSet=bengali_avro:"
    val jatiyoSubtype = "bn_BD:KeyboardLayoutSet=bengali_jatiyo:"
    val probhatSubtype = "bn_BD:KeyboardLayoutSet=bengali_probhat:"

    val isAvroActive = currentSubtypes.contains(avroSubtype)
    val isJatiyoActive = currentSubtypes.contains(jatiyoSubtype)
    val isProbhatActive = currentSubtypes.contains(probhatSubtype)

    fun toggleSubtype(subtypeStr: String, enabled: Boolean) {
        val newSubtypes = if (enabled) {
            currentSubtypes + subtypeStr
        } else {
            if (currentSubtypes.size > 1) currentSubtypes - subtypeStr else currentSubtypes
        }
        context.setSettingBlocking(SubtypesSetting.key, newSubtypes)
        if (!enabled && context.getSettingBlocking(ActiveSubtype) == subtypeStr) {
            context.setSettingBlocking(ActiveSubtype.key, newSubtypes.firstOrNull() ?: "")
        }
    }

    LaunchedEffect(Unit) {
        Subtypes.addDefaultSubtypesIfNecessary(context)

        val result = UpdateChecker.checkForUpdate()
        val update = result.getOrNull()
        if (update != null && update.isUpdateAvailable) {
            availableUpdate = update
            showUpdateSheet = true
        }

        announcementManager.checkForAnnouncements(coroutineScope, forceShow = false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            BrandHero(
                versionName = BuildConfig.VERSION_NAME,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ---- Quick actions -------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionTile(
                    title = "Languages",
                    iconRes = R.drawable.globe,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("languages") }
                )
                QuickActionTile(
                    title = "Themes",
                    iconRes = R.drawable.themes,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("themes") }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionTile(
                    title = "Typing",
                    iconRes = R.drawable.keyboard,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("typing") }
                )
                QuickActionTile(
                    title = "Prediction",
                    iconRes = R.drawable.text_prediction,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("predictiveText") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---- Input & Languages ---------------------------------------------
            SectionHeader(title = "Input & Languages", modifier = Modifier.padding(horizontal = 16.dp))
            NeuCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = 4.dp
            ) {
                NavigationItem(
                    title = "Languages",
                    style = NavigationItemStyle.HomePrimary,
                    navigate = { navController.navigate("languages") },
                    icon = painterResource(id = R.drawable.globe)
                )
                Divider()
                NavigationItem(
                    title = "Keyboard & Typing",
                    style = NavigationItemStyle.HomeSecondary,
                    navigate = { navController.navigate("typing") },
                    icon = painterResource(id = R.drawable.keyboard)
                )
                Divider()
                NavigationItem(
                    title = "Text Prediction",
                    style = NavigationItemStyle.HomeTertiary,
                    navigate = { navController.navigate("predictiveText") },
                    icon = painterResource(id = R.drawable.text_prediction)
                )
                Divider()
                NavigationItem(
                    title = stringResource(R.string.edit_personal_dictionary),
                    style = NavigationItemStyle.HomeSecondary,
                    icon = painterResource(id = R.drawable.book),
                    navigate = {
                        val intent = Intent("android.settings.USER_DICTIONARY_SETTINGS")
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ---- Bengali layouts -----------------------------------------------
            SectionHeader(title = "Bengali Layouts", modifier = Modifier.padding(horizontal = 16.dp))
            NeuCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = 4.dp
            ) {
                BengaliLayoutToggleRow(
                    title = "Avro",
                    subtitle = "Phonetic typing (ami -> আমি)",
                    active = isAvroActive,
                    canToggle = !isAvroActive || currentSubtypes.size > 1,
                    onToggle = { enabled -> toggleSubtype(avroSubtype, enabled) }
                )
                Divider()
                BengaliLayoutToggleRow(
                    title = "Jatiyo",
                    subtitle = "National standard layout",
                    active = isJatiyoActive,
                    canToggle = !isJatiyoActive || currentSubtypes.size > 1,
                    onToggle = { enabled -> toggleSubtype(jatiyoSubtype, enabled) }
                )
                Divider()
                BengaliLayoutToggleRow(
                    title = "Probhat",
                    subtitle = "Probhat keyboard layout",
                    active = isProbhatActive,
                    canToggle = !isProbhatActive || currentSubtypes.size > 1,
                    onToggle = { enabled -> toggleSubtype(probhatSubtype, enabled) }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ---- Customization --------------------------------------------------
            SectionHeader(title = "Customization", modifier = Modifier.padding(horizontal = 16.dp))
            NeuCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = 4.dp
            ) {
                NavigationItem(
                    title = "Keyboard Theme",
                    style = NavigationItemStyle.HomeTertiary,
                    navigate = { navController.navigate("themes") },
                    icon = painterResource(id = R.drawable.themes)
                )
                Divider()
                NavigationItem(
                    title = "Font Style",
                    style = NavigationItemStyle.HomePrimary,
                    navigate = { navController.navigate("fonts") },
                    icon = painterResource(id = R.drawable.file_text)
                )
                Divider()
                NavigationItem(
                    title = "Announcements",
                    style = NavigationItemStyle.HomeSecondary,
                    navigate = {
                        announcementManager.checkForAnnouncements(coroutineScope, forceShow = true) { res ->
                            res.fold(
                                onSuccess = { ann ->
                                    if (ann != null) {
                                        showAnnouncementSheet = true
                                    } else {
                                        Toast.makeText(context, "No new announcements", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onFailure = {
                                    Toast.makeText(context, "No new announcements", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },
                    icon = painterResource(id = R.drawable.ic_campaign)
                )
                if (isDeveloper || LocalInspectionMode.current) {
                    Divider()
                    NavigationItem(
                        title = "Developer Settings",
                        style = NavigationItemStyle.HomeTertiary,
                        navigate = { navController.navigate("developer") },
                        icon = painterResource(id = R.drawable.code)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ---- Updates ---------------------------------------------------------
            SectionHeader(title = "Updates", modifier = Modifier.padding(horizontal = 16.dp))
            NeuCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = 12.dp,
                onClick = {
                    if (!isCheckingUpdate) {
                        coroutineScope.launch {
                            val result = UpdateChecker.checkForUpdate()
                            result.fold(
                                onSuccess = { update ->
                                    if (update.isUpdateAvailable) {
                                        availableUpdate = update
                                        showUpdateSheet = true
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "MyKey is up to date (v${BuildConfig.VERSION_NAME})",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "MyKey is up to date (v${BuildConfig.VERSION_NAME})",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    }
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.activity),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Check for Updates",
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (isCheckingUpdate) "Checking releases..." else "Version ${BuildConfig.VERSION_NAME} - tap to check",
                                fontFamily = Inter,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    if (isCheckingUpdate) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(14.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Check",
                                fontFamily = Inter,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ---- About -----------------------------------------------------------
            SectionHeader(title = "About", modifier = Modifier.padding(horizontal = 16.dp))
            DeveloperDetailsCard(modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(24.dp))
        }

        // ---- Pinned "Try typing" capsule -------------------------------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        val intent = Intent().apply {
                            setClass(context, TextEditPopupActivity::class.java)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        }
                        context.startActivity(intent)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.try_typing),
                    fontFamily = Inter,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

    if (showUpdateSheet && availableUpdate != null) {
        UpdateSheet(
            update = availableUpdate!!,
            onDismiss = { showUpdateSheet = false }
        )
    }

    if (showAnnouncementSheet && availableAnnouncement != null) {
        AnnouncementSheet(
            announcement = availableAnnouncement!!,
            onDismiss = {
                showAnnouncementSheet = false
                availableAnnouncement?.let { announcementManager.dismissAnnouncement(it.id) }
            }
        )
    }
}
