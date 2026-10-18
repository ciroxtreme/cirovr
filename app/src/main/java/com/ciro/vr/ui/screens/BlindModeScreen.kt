package com.ciro.vr.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciro.vr.engine.CiroVREngine
import com.ciro.vr.engine.GameState
import com.ciro.vr.model.GameMode
import com.ciro.vr.ui.components.DebriefDialog
import com.ciro.vr.ui.components.DiceView
import com.ciro.vr.ui.components.NumberPickerRow
import com.ciro.vr.ui.components.StreakCard
import com.ciro.vr.ui.theme.DarkBackground
import com.ciro.vr.ui.theme.DarkBorder
import com.ciro.vr.ui.theme.DarkSurface
import com.ciro.vr.ui.theme.NearMissOrange
import com.ciro.vr.ui.theme.PeachDark
import com.ciro.vr.ui.theme.PeachPrimary
import com.ciro.vr.ui.theme.TextPrimaryDark
import com.ciro.vr.ui.theme.TextSecondaryDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BlindModeScreen(
    state: GameState,
    engine: CiroVREngine,
    onNavigateToLab: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Mystery Mode Header
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
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = PeachPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "MODE UJI BUTA (THE BLIND TEST)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = PeachPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Algoritma rahasia aktif di balik layar. Bisakah egomu membedakan manipulasi dan keacakan?",
                        fontSize = 12.sp,
                        color = TextSecondaryDark,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Streak Card
        StreakCard(
            currentRound = state.currentRound,
            currentStreak = state.currentStreak,
            history = state.history
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Animated Dice Centerpiece
        DiceView(
            number = state.lastRolledNumber,
            isRolling = state.isRolling,
            size = 135.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selection instructions
        Text(
            text = "Pilih Tebakan Angka Dadu:",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondaryDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 1..6 Number Picker
        NumberPickerRow(
            selectedNumber = state.selectedGuess,
            onSelect = { engine.setGuess(it) },
            enabled = !state.isRolling
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Roll Action Button
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
                text = if (state.isRolling) "Mengocok Dadu..." else "KOCOK DADU SEKARANG",
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp,
                color = DarkBackground
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Reveal Reality Button
        val canReveal = state.currentRound > 3
        OutlinedButton(
            onClick = { engine.showDebrief() },
            enabled = canReveal,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = if (canReveal) PeachPrimary else TextSecondaryDark.copy(alpha = 0.5f)
            ),
            border = ButtonDefaults.outlinedButtonBorder().copy(
                brush = Brush.horizontalGradient(
                    if (canReveal) listOf(PeachPrimary, PeachDark) else listOf(DarkBorder, DarkBorder)
                )
            )
        ) {
            Icon(
                imageVector = Icons.Default.Visibility,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (canReveal) "BUKA TABIR REALITA (DEBRIEF)" else "Buka Tabir (Minimal 3 Putaran)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Debrief Dialog
    if (state.isDebriefVisible) {
        DebriefDialog(
            state = state,
            onDismiss = { engine.hideDebrief() },
            onReset = { engine.resetGame() },
            onGoToMatrixLab = {
                engine.setGameMode(GameMode.CONSCIOUS)
                onNavigateToLab()
            }
        )
    }
}
