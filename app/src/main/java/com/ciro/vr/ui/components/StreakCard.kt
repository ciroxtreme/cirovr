package com.ciro.vr.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciro.vr.model.RollHistory
import com.ciro.vr.ui.theme.DarkBackground
import com.ciro.vr.ui.theme.DarkBorder
import com.ciro.vr.ui.theme.DarkSurface
import com.ciro.vr.ui.theme.LossRed
import com.ciro.vr.ui.theme.NearMissOrange
import com.ciro.vr.ui.theme.NeutralGold
import com.ciro.vr.ui.theme.PeachPrimary
import com.ciro.vr.ui.theme.TextPrimaryDark
import com.ciro.vr.ui.theme.TextSecondaryDark
import com.ciro.vr.ui.theme.WinGreen
import kotlin.math.abs

@Composable
fun StreakCard(
    currentRound: Int,
    currentStreak: Int,
    history: List<RollHistory>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkBorder))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Putaran #$currentRound",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PeachPrimary
                )

                // Streak Badge
                val streakText = when {
                    currentStreak > 1 -> "🔥 Win Streak: ${currentStreak}x"
                    currentStreak == 1 -> "✅ Menang 1x"
                    currentStreak < -1 -> "❄️ Kalah Streak: ${abs(currentStreak)}x"
                    currentStreak == -1 -> "❌ Kalah 1x"
                    else -> "Mulai Kocokan"
                }
                val streakBg = when {
                    currentStreak > 0 -> WinGreen.copy(alpha = 0.2f)
                    currentStreak < 0 -> LossRed.copy(alpha = 0.2f)
                    else -> DarkBorder
                }
                val streakColor = when {
                    currentStreak > 0 -> WinGreen
                    currentStreak < 0 -> LossRed
                    else -> TextSecondaryDark
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(streakBg)
                        .border(1.dp, streakColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = streakText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = streakColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Psychology Whisper
            val whisperText = when {
                currentStreak >= 3 -> "🧠 Egomu mulai membisikkan ilusi dewa ('Aku sakti!'). Waspadalah."
                currentStreak <= -3 -> "🥀 Otakmu mulai merasa terkutuk semesta ('Kenapa aku sial?!'). Tenang, ini hanya variansi angka."
                history.firstOrNull()?.isNearMiss == true -> "⚡ Meleset 1 angka! Semakin tipis melesetnya, semakin kuat otakmu terobsesi mengejarnya."
                else -> "🎲 Perhatikan perasaan di dadamu saat dadu berhenti berputar."
            }

            Text(
                text = whisperText,
                fontSize = 12.sp,
                color = TextSecondaryDark,
                lineHeight = 17.sp
            )

            if (history.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Riwayat Lemparan Terakhir:",
                    fontSize = 11.sp,
                    color = TextSecondaryDark.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(history.take(12)) { item ->
                        val badgeColor = if (item.isWin) WinGreen else if (item.isNearMiss) NearMissOrange else LossRed
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(badgeColor.copy(alpha = 0.15f))
                                .border(1.dp, badgeColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${item.actual}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = badgeColor
                            )
                        }
                    }
                }
            }
        }
    }
}
