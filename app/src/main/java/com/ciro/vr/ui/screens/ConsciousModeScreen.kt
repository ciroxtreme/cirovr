package com.ciro.vr.ui.screens

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciro.vr.engine.CiroVREngine
import com.ciro.vr.engine.GameState
import com.ciro.vr.model.AlgorithmType
import com.ciro.vr.model.Phase
import com.ciro.vr.ui.components.DiceView
import com.ciro.vr.ui.components.NumberPickerRow
import com.ciro.vr.ui.components.StreakCard
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ConsciousModeScreen(
    state: GameState,
    engine: CiroVREngine,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val activeAlgo = state.selectedConsciousAlgorithm
    val currentPhase = state.currentPhase

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Matrix Lab Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NeutralGold, DarkBorder)))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Science,
                    contentDescription = null,
                    tint = NeutralGold,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "MATRIX LAB (MODE SADAR)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = NeutralGold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Laboratorium transparan. Amati bagaimana egomu bereaksi saat kamu tahu kamu dimanipulasi.",
                        fontSize = 12.sp,
                        color = TextSecondaryDark,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Algorithm Selector Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AlgorithmType.entries.forEach { algo ->
                val isSelected = (algo == activeAlgo)
                val tabColor = when (algo) {
                    AlgorithmType.GOD -> NeutralGold
                    AlgorithmType.CURSE -> LossRed
                    AlgorithmType.FAIR -> WinGreen
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) tabColor.copy(alpha = 0.2f) else DarkSurface)
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) tabColor else DarkBorder,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { engine.setConsciousAlgorithm(algo) }
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (algo) {
                            AlgorithmType.GOD -> "Dewa"
                            AlgorithmType.CURSE -> "Kutukan"
                            AlgorithmType.FAIR -> "Netral 1/6"
                        },
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) tabColor else TextSecondaryDark
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Transparent Phase Telemetry Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkBorder))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Status Telemetri Fase:",
                        fontSize = 12.sp,
                        color = TextSecondaryDark
                    )
                    Text(
                        text = currentPhase.stageName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (currentPhase) {
                            Phase.PHASE_1_BAIT -> WinGreen
                            Phase.PHASE_2_CHAOS -> NeutralGold
                            Phase.PHASE_3_REALITY -> LossRed
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Phase Progress Bar
                val progress = when (currentPhase) {
                    Phase.PHASE_1_BAIT -> (state.currentRound / 6f).coerceIn(0.1f, 1f)
                    Phase.PHASE_2_CHAOS -> ((state.currentRound - 6) / 6f).coerceIn(0.1f, 1f)
                    Phase.PHASE_3_REALITY -> 1f
                }
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape),
                    color = when (currentPhase) {
                        Phase.PHASE_1_BAIT -> WinGreen
                        Phase.PHASE_2_CHAOS -> NeutralGold
                        Phase.PHASE_3_REALITY -> LossRed
                    },
                    trackColor = DarkBackground
                )

                Spacer(modifier = Modifier.height(10.dp))

                val behaviorDesc = when (activeAlgo) {
                    AlgorithmType.GOD -> currentPhase.godBehavior
                    AlgorithmType.CURSE -> currentPhase.curseBehavior
                    AlgorithmType.FAIR -> "Murni acak 1..6 tanpa manipulasi sistem."
                }

                Text(
                    text = "⚙️ Logika Dadu Saat Ini:\n$behaviorDesc",
                    fontSize = 12.sp,
                    color = TextPrimaryDark,
                    lineHeight = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Streak Card
        StreakCard(
            currentRound = state.currentRound,
            currentStreak = state.currentStreak,
            history = state.history
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dice View
        DiceView(
            number = state.lastRolledNumber,
            isRolling = state.isRolling,
            size = 130.dp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Number Picker
        Text(
            text = "Tebak Angka Dadu:",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondaryDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        NumberPickerRow(
            selectedNumber = state.selectedGuess,
            onSelect = { engine.setGuess(it) },
            enabled = !state.isRolling
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Roll Button
        Button(
            onClick = {
                if (!state.isRolling) {
                    scope.launch {
                        engine.startRoll()
                        delay(850)
                        engine.completeRoll(state.selectedGuess)
                    }
                }
            },
            enabled = !state.isRolling,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PeachPrimary)
        ) {
            Icon(
                imageVector = Icons.Default.Casino,
                contentDescription = null,
                tint = DarkBackground,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = if (state.isRolling) "Mengocok Dadu..." else "UJI LEMPARAN DADU",
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = DarkBackground
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Stoic Mindfulness Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkBorder))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🧘 Catatan Pengamat (Mindfulness):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PeachPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Perhatikan detak jantung dan sensasi di tubuhmu: Apakah saat tebakanmu benar kamu tetap merasakan lonjakan kepuasan padahal kamu tahu ini diskenariokan? Di sinilah kelemahan biologis manusia terhadap variabel rasio.",
                    fontSize = 12.sp,
                    color = TextSecondaryDark,
                    lineHeight = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
