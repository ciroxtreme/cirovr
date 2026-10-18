package com.ciro.vr.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciro.vr.ui.theme.DarkBackground
import com.ciro.vr.ui.theme.DarkBorder
import com.ciro.vr.ui.theme.DarkSurface
import com.ciro.vr.ui.theme.DarkSurfaceVariant
import com.ciro.vr.ui.theme.LossRed
import com.ciro.vr.ui.theme.NeutralGold
import com.ciro.vr.ui.theme.PeachDark
import com.ciro.vr.ui.theme.PeachPrimary
import com.ciro.vr.ui.theme.TextPrimaryDark
import com.ciro.vr.ui.theme.TextSecondaryDark
import com.ciro.vr.ui.theme.WinGreen

@Composable
fun PhilosophyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Title Header
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
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = PeachPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "FILOSOFI & KAMUS MENTAL",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = PeachPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Landasan psikologi mengapa CiroVR dirancang untuk melatih mental Stoikmu.",
                        fontSize = 12.sp,
                        color = TextSecondaryDark,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 1: Variable Ratio
        ConceptCard(
            icon = Icons.Default.Psychology,
            iconTint = PeachPrimary,
            title = "Hadiah Intermiten & Variabel Rasio",
            subtitle = "B.F. Skinner (Operant Conditioning)",
            body = "Otak manusia tidak menjadi kecanduan saat selalu menang atau selalu kalah. Manusia paling terikat saat hadiah datang secara tidak terduga (Intermittent / Variable Ratio). Ketidakpastian inilah yang membajak dopamin dan membuat pemain merasa 'sedikit lagi pasti tembus'."
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Card 2: Illusion of Control
        ConceptCard(
            icon = Icons.Default.AutoAwesome,
            iconTint = NeutralGold,
            title = "Ilusi Kendali (Illusion of Control)",
            subtitle = "Ellen Langer (1975)",
            body = "Saat seseorang mengalami kemenangan beruntun (win streak) pada peristiwa acak murni, egonya mulai meyakini bahwa ia memiliki keahlian atau intuisi magis untuk mengendalikan masa depan. Di sinilah sindrom 'Masuk Jadi Dewa' lahir."
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Card 3: Learned Helplessness
        ConceptCard(
            icon = Icons.Default.Shield,
            iconTint = LossRed,
            title = "Ketakberdayaan Terpelajari",
            subtitle = "Martin Seligman (Learned Helplessness)",
            body = "Sebaliknya, saat seseorang dihantam kesialan beruntun (terutama yang meleset tipis), otak mulai mengembangkan paranoia bahwa dirinya 'dikutuk semesta' dan menjadi korban takdir. Padahal kesialan itu hanyalah rentetan klaster acak biasa."
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Card 4: Stoic Principle
        ConceptCard(
            icon = Icons.Default.Balance,
            iconTint = WinGreen,
            title = "Prinsip Stoik: Keacakan Itu Netral",
            subtitle = "Amor Fati & Dikotomi Kendali",
            body = "Dadu tidak mencintaimu dan tidak membencimu. Angka 1 sampai 6 tidak memiliki dendam pribadi pada siapa pun. Saat kamu menang, jangan sombong merasa Dewa. Saat kamu kalah, jangan tenggelam merasa Budak. Tetaplah tenang di tengah pasang surut ketidakpastian."
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ConceptCard(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    body: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkBorder))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(iconTint.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryDark
                    )
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = iconTint
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = body,
                fontSize = 12.sp,
                color = TextSecondaryDark,
                lineHeight = 17.sp
            )
        }
    }
}
