package com.ciro.vr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciro.vr.engine.CiroVREngine
import com.ciro.vr.model.GameMode
import com.ciro.vr.ui.screens.BlindModeScreen
import com.ciro.vr.ui.screens.ConsciousModeScreen
import com.ciro.vr.ui.screens.PhilosophyScreen
import com.ciro.vr.ui.theme.CiroVRTheme
import com.ciro.vr.ui.theme.DarkBackground
import com.ciro.vr.ui.theme.DarkBorder
import com.ciro.vr.ui.theme.DarkSurface
import com.ciro.vr.ui.theme.PeachPrimary
import com.ciro.vr.ui.theme.TextPrimaryDark
import com.ciro.vr.ui.theme.TextSecondaryDark

class MainActivity : ComponentActivity() {

    private val engine = CiroVREngine()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CiroVRTheme {
                var selectedTabIndex by remember { mutableIntStateOf(0) }
                val state by engine.state.collectAsState()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                androidx.compose.foundation.layout.Column {
                                    Text(
                                        text = "CiroVR",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = PeachPrimary
                                    )
                                    Text(
                                        text = "Mental Stoic & Variable Ratio",
                                        fontSize = 11.sp,
                                        color = TextSecondaryDark
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { engine.resetGame() }) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Reset Sesi",
                                        tint = PeachPrimary
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = DarkSurface,
                                titleContentColor = TextPrimaryDark
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = DarkSurface,
                            tonalElevation = 8.dp
                        ) {
                            val items = listOf(
                                Triple("Uji Buta", Icons.Default.Lock, 0),
                                Triple("Matrix Lab", Icons.Default.Science, 1),
                                Triple("Filosofi", Icons.Default.MenuBook, 2)
                            )

                            items.forEach { (label, icon, index) ->
                                NavigationBarItem(
                                    selected = (selectedTabIndex == index),
                                    onClick = {
                                        selectedTabIndex = index
                                        if (index == 0) {
                                            engine.setGameMode(GameMode.BLIND)
                                        } else if (index == 1) {
                                            engine.setGameMode(GameMode.CONSCIOUS)
                                        }
                                    },
                                    icon = { Icon(imageVector = icon, contentDescription = label) },
                                    label = {
                                        Text(
                                            text = label,
                                            fontSize = 11.sp,
                                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = DarkBackground,
                                        selectedTextColor = PeachPrimary,
                                        indicatorColor = PeachPrimary,
                                        unselectedIconColor = TextSecondaryDark,
                                        unselectedTextColor = TextSecondaryDark
                                    )
                                )
                            }
                        }
                    },
                    containerColor = DarkBackground
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (selectedTabIndex) {
                            0 -> BlindModeScreen(
                                state = state,
                                engine = engine,
                                onNavigateToLab = { selectedTabIndex = 1 }
                            )
                            1 -> ConsciousModeScreen(
                                state = state,
                                engine = engine
                            )
                            2 -> PhilosophyScreen()
                        }
                    }
                }
            }
        }
    }
}
