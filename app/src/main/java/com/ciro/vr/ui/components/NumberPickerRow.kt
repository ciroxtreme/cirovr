package com.ciro.vr.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciro.vr.ui.theme.DarkBackground
import com.ciro.vr.ui.theme.DarkBorder
import com.ciro.vr.ui.theme.DarkSurfaceVariant
import com.ciro.vr.ui.theme.PeachPrimary
import com.ciro.vr.ui.theme.TextPrimaryDark
import com.ciro.vr.ui.theme.TextSecondaryDark

@Composable
fun NumberPickerRow(
    selectedNumber: Int,
    onSelect: (Int) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        (1..6).forEach { number ->
            val isSelected = (number == selectedNumber)

            val bgColor by animateColorAsState(
                targetValue = if (isSelected) PeachPrimary else DarkSurfaceVariant,
                label = "btn_bg"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) DarkBackground else TextPrimaryDark,
                label = "btn_text"
            )
            val elevation by animateDpAsState(
                targetValue = if (isSelected) 8.dp else 0.dp,
                label = "btn_elev"
            )

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(elevation, shape = CircleShape, spotColor = PeachPrimary)
                    .clip(CircleShape)
                    .background(bgColor)
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) Color.White.copy(alpha = 0.6f) else DarkBorder,
                        shape = CircleShape
                    )
                    .clickable(
                        enabled = enabled,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = true, color = PeachPrimary)
                    ) {
                        onSelect(number)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$number",
                    color = textColor,
                    fontSize = 18.sp,
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold
                )
            }
        }
    }
}
