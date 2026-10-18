package com.ciro.vr.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ciro.vr.engine.GameState
import com.ciro.vr.model.AlgorithmType
import com.ciro.vr.ui.theme.CreamBackground
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
fun DebriefDialog(
    state: GameState,
    onDismiss: () -> Unit,
    onReset: () -> Unit,
    onGoToMatrixLab: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.88f),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = DarkBackground),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(PeachPrimary, DarkBorder)))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header Banner
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(PeachPrimary.copy(alpha = 0.2f))
                            .border(1.5.dp, PeachPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = PeachPrimary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "TABIR REALITA TERBUKA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp,
                            color = PeachPrimary
                        )
                        Text(
                            text = "Bedah Psikologis Sesi",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // The Secret Algorithm Card
                val algo = state.activeAlgorithm
                val algoColor = if (algo == AlgorithmType.GOD) NeutralGold else NearMissOrange

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(algoColor, DarkBorder)))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Algoritma yang Mempermainkanmu:",
                            fontSize = 12.sp,
                            color = TextSecondaryDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = algo.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = algoColor
                        )
                        Text(
                            text = algo.subtitle,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = algoColor.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 3-Act Psychological Narrative
                Text(
                    text = "Refleksi Tiga Babak:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (algo == AlgorithmType.GOD) {
                    // GOD MODE 3-ACTS
                    ActCard(
                        actNumber = "1",
                        actTitle = state.settings.godAct1Title,
                        description = state.settings.godAct1Text,
                        badgeColor = WinGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ActCard(
                        actNumber = "2",
                        actTitle = state.settings.godAct2Title,
                        description = state.settings.godAct2Text,
                        badgeColor = NeutralGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ActCard(
                        actNumber = "3",
                        actTitle = state.settings.godAct3Title,
                        description = state.settings.godAct3Text,
                        badgeColor = LossRed
                    )
                } else {
                    // CURSE MODE 3-ACTS
                    ActCard(
                        actNumber = "1",
                        actTitle = state.settings.curseAct1Title,
                        description = state.settings.curseAct1Text,
                        badgeColor = LossRed
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ActCard(
                        actNumber = "2",
                        actTitle = state.settings.curseAct2Title,
                        description = state.settings.curseAct2Text,
                        badgeColor = NearMissOrange
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ActCard(
                        actNumber = "3",
                        actTitle = state.settings.curseAct3Title,
                        description = state.settings.curseAct3Text,
                        badgeColor = WinGreen
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Stats Grid
                Text(
                    text = "Statistik Emosi & Angka:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatBox(
                        label = "Total Lemparan",
                        value = "${state.currentRound - 1}",
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        label = "Menang / Kalah",
                        value = "${state.totalWins} / ${state.totalLosses}",
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        label = "Max Win Streak",
                        value = "${state.maxWinStreak}x",
                        modifier = Modifier.weight(1f),
                        valueColor = WinGreen
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons
                Button(
                    onClick = {
                        onReset()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PeachPrimary)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = DarkBackground)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ulangi Siklus (Reset Sesi Baru)",
                        color = DarkBackground,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        onGoToMatrixLab()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PeachPrimary),
                    border = ButtonDefaults.outlinedButtonBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(PeachDark))
                ) {
                    Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = PeachPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Uji di Matrix Lab (Mode Sadar)", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun ActCard(
    actNumber: String,
    actTitle: String,
    description: String,
    badgeColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkBorder))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(badgeColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = actNumber,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = DarkBackground
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = actTitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = TextSecondaryDark,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
private fun StatBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = TextPrimaryDark
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(DarkSurfaceVariant)
            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
            .padding(vertical = 10.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                color = TextSecondaryDark
            )
        }
    }
}
