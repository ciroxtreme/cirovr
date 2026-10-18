package com.ciro.vr.ui.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciro.vr.engine.CiroVREngine
import com.ciro.vr.engine.SettingsManager
import com.ciro.vr.model.GameSettings
import com.ciro.vr.ui.theme.DarkBackground
import com.ciro.vr.ui.theme.DarkBorder
import com.ciro.vr.ui.theme.DarkSurface
import com.ciro.vr.ui.theme.DarkSurfaceVariant
import com.ciro.vr.ui.theme.LossRed
import com.ciro.vr.ui.theme.NearMissOrange
import com.ciro.vr.ui.theme.NeutralGold
import com.ciro.vr.ui.theme.PeachDark
import com.ciro.vr.ui.theme.PeachPrimary
import com.ciro.vr.ui.theme.TextPrimaryDark
import com.ciro.vr.ui.theme.TextSecondaryDark
import com.ciro.vr.ui.theme.WinGreen

@Composable
fun SettingsScreen(
    engine: CiroVREngine,
    settingsManager: SettingsManager,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentSettings = engine.state.value.settings

    // Local mutable states for editing
    var phase1Rounds by remember(currentSettings) { mutableIntStateOf(currentSettings.phase1Rounds) }
    var phase2Rounds by remember(currentSettings) { mutableIntStateOf(currentSettings.phase2Rounds) }
    var phase3Rounds by remember(currentSettings) { mutableIntStateOf(currentSettings.phase3Rounds) }

    var godPhase1WinRate by remember(currentSettings) { mutableIntStateOf(currentSettings.godPhase1WinRate) }
    var godPhase2WinRate by remember(currentSettings) { mutableIntStateOf(currentSettings.godPhase2WinRate) }
    var godPhase3WinRate by remember(currentSettings) { mutableIntStateOf(currentSettings.godPhase3WinRate) }

    var cursePhase1WinRate by remember(currentSettings) { mutableIntStateOf(currentSettings.cursePhase1WinRate) }
    var cursePhase2WinRate by remember(currentSettings) { mutableIntStateOf(currentSettings.cursePhase2WinRate) }
    var cursePhase3WinRate by remember(currentSettings) { mutableIntStateOf(currentSettings.cursePhase3WinRate) }

    var nearMissRate by remember(currentSettings) { mutableIntStateOf(currentSettings.nearMissRate) }

    // Narasi Dewa
    var godAct1Title by remember(currentSettings) { mutableStateOf(currentSettings.godAct1Title) }
    var godAct1Text by remember(currentSettings) { mutableStateOf(currentSettings.godAct1Text) }
    var godAct2Title by remember(currentSettings) { mutableStateOf(currentSettings.godAct2Title) }
    var godAct2Text by remember(currentSettings) { mutableStateOf(currentSettings.godAct2Text) }
    var godAct3Title by remember(currentSettings) { mutableStateOf(currentSettings.godAct3Title) }
    var godAct3Text by remember(currentSettings) { mutableStateOf(currentSettings.godAct3Text) }

    // Narasi Kutukan
    var curseAct1Title by remember(currentSettings) { mutableStateOf(currentSettings.curseAct1Title) }
    var curseAct1Text by remember(currentSettings) { mutableStateOf(currentSettings.curseAct1Text) }
    var curseAct2Title by remember(currentSettings) { mutableStateOf(currentSettings.curseAct2Title) }
    var curseAct2Text by remember(currentSettings) { mutableStateOf(currentSettings.curseAct2Text) }
    var curseAct3Title by remember(currentSettings) { mutableStateOf(currentSettings.curseAct3Title) }
    var curseAct3Text by remember(currentSettings) { mutableStateOf(currentSettings.curseAct3Text) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(PeachPrimary, DarkBorder)))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = PeachPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "PENGATURAN & KUSTOMISASI",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = PeachPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Kustomisasi susunan pola dadu dan narasi refleksi psikologis sesukamu.",
                        fontSize = 12.sp,
                        color = TextSecondaryDark,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SECTION 1: Pola Dadu & Durasi Putaran
        SectionTitle(title = "🎲 Susunan Pola Dadu & Putaran Fase", iconTint = PeachPrimary)
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkBorder))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Presets
                Text(
                    text = "Preset Cepat Pola Putaran:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PresetChip(
                        text = "5 - 5 - 5 (Standar)",
                        isSelected = (phase1Rounds == 5 && phase2Rounds == 5 && phase3Rounds == 5),
                        onClick = {
                            phase1Rounds = 5
                            phase2Rounds = 5
                            phase3Rounds = 5
                        },
                        modifier = Modifier.weight(1f)
                    )
                    PresetChip(
                        text = "7 - 5 - 7 (Intens)",
                        isSelected = (phase1Rounds == 7 && phase2Rounds == 5 && phase3Rounds == 7),
                        onClick = {
                            phase1Rounds = 7
                            phase2Rounds = 5
                            phase3Rounds = 7
                        },
                        modifier = Modifier.weight(1f)
                    )
                    PresetChip(
                        text = "3 - 3 - 3 (Cepat)",
                        isSelected = (phase1Rounds == 3 && phase2Rounds == 3 && phase3Rounds == 3),
                        onClick = {
                            phase1Rounds = 3
                            phase2Rounds = 3
                            phase3Rounds = 3
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Phase 1 Stepper
                RoundStepper(
                    label = "Fase 1: Umpan (Bait)",
                    value = phase1Rounds,
                    onValueChange = { phase1Rounds = it },
                    accentColor = WinGreen
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Phase 2 Stepper
                RoundStepper(
                    label = "Fase 2: Keraguan (Chaos)",
                    value = phase2Rounds,
                    onValueChange = { phase2Rounds = it },
                    accentColor = NeutralGold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Phase 3 Stepper
                RoundStepper(
                    label = "Fase 3: Realita (Crash)",
                    value = phase3Rounds,
                    onValueChange = { phase3Rounds = it },
                    accentColor = LossRed
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Probability Sliders
                Text(
                    text = "Peluang Meleset Tipis (Near-Miss): $nearMissRate%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryDark
                )
                Slider(
                    value = nearMissRate.toFloat(),
                    onValueChange = { nearMissRate = it.toInt() },
                    valueRange = 10f..100f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = NearMissOrange,
                        activeTrackColor = NearMissOrange,
                        inactiveTrackColor = DarkBackground
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // SECTION 2: Narasi Algoritma Dewa
        SectionTitle(title = "👑 Narasi Algoritma Dewa", iconTint = NeutralGold)
        Spacer(modifier = Modifier.height(8.dp))

        NarasiEditorCard(
            actNumber = "1",
            title = godAct1Title,
            onTitleChange = { godAct1Title = it },
            text = godAct1Text,
            onTextChange = { godAct1Text = it },
            badgeColor = WinGreen
        )

        Spacer(modifier = Modifier.height(8.dp))

        NarasiEditorCard(
            actNumber = "2",
            title = godAct2Title,
            onTitleChange = { godAct2Title = it },
            text = godAct2Text,
            onTextChange = { godAct2Text = it },
            badgeColor = NeutralGold
        )

        Spacer(modifier = Modifier.height(8.dp))

        NarasiEditorCard(
            actNumber = "3",
            title = godAct3Title,
            onTitleChange = { godAct3Title = it },
            text = godAct3Text,
            onTextChange = { godAct3Text = it },
            badgeColor = LossRed
        )

        Spacer(modifier = Modifier.height(18.dp))

        // SECTION 3: Narasi Algoritma Kutukan
        SectionTitle(title = "🥀 Narasi Algoritma Kutukan", iconTint = LossRed)
        Spacer(modifier = Modifier.height(8.dp))

        NarasiEditorCard(
            actNumber = "1",
            title = curseAct1Title,
            onTitleChange = { curseAct1Title = it },
            text = curseAct1Text,
            onTextChange = { curseAct1Text = it },
            badgeColor = LossRed
        )

        Spacer(modifier = Modifier.height(8.dp))

        NarasiEditorCard(
            actNumber = "2",
            title = curseAct2Title,
            onTitleChange = { curseAct2Title = it },
            text = curseAct2Text,
            onTextChange = { curseAct2Text = it },
            badgeColor = NearMissOrange
        )

        Spacer(modifier = Modifier.height(8.dp))

        NarasiEditorCard(
            actNumber = "3",
            title = curseAct3Title,
            onTitleChange = { curseAct3Title = it },
            text = curseAct3Text,
            onTextChange = { curseAct3Text = it },
            badgeColor = WinGreen
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ACTION BUTTONS
        Button(
            onClick = {
                val updated = GameSettings(
                    phase1Rounds = phase1Rounds,
                    phase2Rounds = phase2Rounds,
                    phase3Rounds = phase3Rounds,
                    godPhase1WinRate = godPhase1WinRate,
                    godPhase2WinRate = godPhase2WinRate,
                    godPhase3WinRate = godPhase3WinRate,
                    cursePhase1WinRate = cursePhase1WinRate,
                    cursePhase2WinRate = cursePhase2WinRate,
                    cursePhase3WinRate = cursePhase3WinRate,
                    nearMissRate = nearMissRate,
                    godAct1Title = godAct1Title,
                    godAct1Text = godAct1Text,
                    godAct2Title = godAct2Title,
                    godAct2Text = godAct2Text,
                    godAct3Title = godAct3Title,
                    godAct3Text = godAct3Text,
                    curseAct1Title = curseAct1Title,
                    curseAct1Text = curseAct1Text,
                    curseAct2Title = curseAct2Title,
                    curseAct2Text = curseAct2Text,
                    curseAct3Title = curseAct3Title,
                    curseAct3Text = curseAct3Text
                )
                settingsManager.updateSettings(updated)
                engine.updateSettings(updated)
                Toast.makeText(context, "Pengaturan & Narasi Berhasil Disimpan! ✅", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PeachPrimary)
        ) {
            Icon(imageVector = Icons.Default.Save, contentDescription = null, tint = DarkBackground)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "SIMPAN PERUBAHAN", fontWeight = FontWeight.Bold, color = DarkBackground)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = {
                settingsManager.resetToDefault()
                val def = GameSettings()
                engine.updateSettings(def)
                // refresh local states
                phase1Rounds = def.phase1Rounds
                phase2Rounds = def.phase2Rounds
                phase3Rounds = def.phase3Rounds
                godPhase1WinRate = def.godPhase1WinRate
                godPhase2WinRate = def.godPhase2WinRate
                godPhase3WinRate = def.godPhase3WinRate
                cursePhase1WinRate = def.cursePhase1WinRate
                cursePhase2WinRate = def.cursePhase2WinRate
                cursePhase3WinRate = def.cursePhase3WinRate
                nearMissRate = def.nearMissRate
                godAct1Title = def.godAct1Title
                godAct1Text = def.godAct1Text
                godAct2Title = def.godAct2Title
                godAct2Text = def.godAct2Text
                godAct3Title = def.godAct3Title
                godAct3Text = def.godAct3Text
                curseAct1Title = def.curseAct1Title
                curseAct1Text = def.curseAct1Text
                curseAct2Title = def.curseAct2Title
                curseAct2Text = def.curseAct2Text
                curseAct3Title = def.curseAct3Title
                curseAct3Text = def.curseAct3Text
                Toast.makeText(context, "Dipulihkan ke Pengaturan Bawaan! 🔄", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = LossRed),
            border = ButtonDefaults.outlinedButtonBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(LossRed.copy(alpha = 0.5f)))
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = LossRed)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Pulihkan Teks & Susunan Bawaan", fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun SectionTitle(
    title: String,
    iconTint: androidx.compose.ui.graphics.Color
) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimaryDark
    )
}

@Composable
private fun PresetChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) PeachPrimary else DarkSurface)
            .border(1.dp, if (isSelected) PeachPrimary else DarkBorder, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) DarkBackground else TextSecondaryDark
        )
    }
}

@Composable
private fun RoundStepper(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    accentColor: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimaryDark)
            Text(text = "Jumlah lemparan dadu", fontSize = 10.sp, color = TextSecondaryDark)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { if (value > 2) onValueChange(value - 1) },
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(DarkSurface)
                    .border(1.dp, DarkBorder, CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = null, tint = TextPrimaryDark, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "$value lemparan",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                onClick = { if (value < 20) onValueChange(value + 1) },
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(DarkSurface)
                    .border(1.dp, DarkBorder, CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = TextPrimaryDark, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun NarasiEditorCard(
    actNumber: String,
    title: String,
    onTitleChange: (String) -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    badgeColor: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkBorder))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(badgeColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = actNumber, fontSize = 11.sp, fontWeight = FontWeight.Black, color = DarkBackground)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Babak #$actNumber", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimaryDark)
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text("Judul Babak") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PeachPrimary,
                    unfocusedBorderColor = DarkBorder,
                    focusedLabelColor = PeachPrimary,
                    unfocusedLabelColor = TextSecondaryDark,
                    focusedTextColor = TextPrimaryDark,
                    unfocusedTextColor = TextPrimaryDark
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                label = { Text("Isi Narasi Refleksi") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 6,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PeachPrimary,
                    unfocusedBorderColor = DarkBorder,
                    focusedLabelColor = PeachPrimary,
                    unfocusedLabelColor = TextSecondaryDark,
                    focusedTextColor = TextPrimaryDark,
                    unfocusedTextColor = TextPrimaryDark
                )
            )
        }
    }
}
