package com.ciro.vr.engine

import android.content.Context
import android.content.SharedPreferences
import com.ciro.vr.model.GameSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("cirovr_settings", Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow(loadSettings())
    val settings: StateFlow<GameSettings> = _settings.asStateFlow()

    fun updateSettings(newSettings: GameSettings) {
        prefs.edit()
            .putInt("phase1Rounds", newSettings.phase1Rounds)
            .putInt("phase2Rounds", newSettings.phase2Rounds)
            .putInt("phase3Rounds", newSettings.phase3Rounds)
            .putInt("godPhase1WinRate", newSettings.godPhase1WinRate)
            .putInt("godPhase2WinRate", newSettings.godPhase2WinRate)
            .putInt("godPhase3WinRate", newSettings.godPhase3WinRate)
            .putInt("cursePhase1WinRate", newSettings.cursePhase1WinRate)
            .putInt("cursePhase2WinRate", newSettings.cursePhase2WinRate)
            .putInt("cursePhase3WinRate", newSettings.cursePhase3WinRate)
            .putInt("nearMissRate", newSettings.nearMissRate)
            .putString("godAct1Title", newSettings.godAct1Title)
            .putString("godAct1Text", newSettings.godAct1Text)
            .putString("godAct2Title", newSettings.godAct2Title)
            .putString("godAct2Text", newSettings.godAct2Text)
            .putString("godAct3Title", newSettings.godAct3Title)
            .putString("godAct3Text", newSettings.godAct3Text)
            .putString("curseAct1Title", newSettings.curseAct1Title)
            .putString("curseAct1Text", newSettings.curseAct1Text)
            .putString("curseAct2Title", newSettings.curseAct2Title)
            .putString("curseAct2Text", newSettings.curseAct2Text)
            .putString("curseAct3Title", newSettings.curseAct3Title)
            .putString("curseAct3Text", newSettings.curseAct3Text)
            .apply()
        _settings.value = newSettings
    }

    fun resetToDefault() {
        prefs.edit().clear().apply()
        _settings.value = GameSettings()
    }

    private fun loadSettings(): GameSettings {
        val default = GameSettings()
        return GameSettings(
            phase1Rounds = prefs.getInt("phase1Rounds", default.phase1Rounds),
            phase2Rounds = prefs.getInt("phase2Rounds", default.phase2Rounds),
            phase3Rounds = prefs.getInt("phase3Rounds", default.phase3Rounds),
            godPhase1WinRate = prefs.getInt("godPhase1WinRate", default.godPhase1WinRate),
            godPhase2WinRate = prefs.getInt("godPhase2WinRate", default.godPhase2WinRate),
            godPhase3WinRate = prefs.getInt("godPhase3WinRate", default.godPhase3WinRate),
            cursePhase1WinRate = prefs.getInt("cursePhase1WinRate", default.cursePhase1WinRate),
            cursePhase2WinRate = prefs.getInt("cursePhase2WinRate", default.cursePhase2WinRate),
            cursePhase3WinRate = prefs.getInt("cursePhase3WinRate", default.cursePhase3WinRate),
            nearMissRate = prefs.getInt("nearMissRate", default.nearMissRate),
            godAct1Title = prefs.getString("godAct1Title", default.godAct1Title) ?: default.godAct1Title,
            godAct1Text = prefs.getString("godAct1Text", default.godAct1Text) ?: default.godAct1Text,
            godAct2Title = prefs.getString("godAct2Title", default.godAct2Title) ?: default.godAct2Title,
            godAct2Text = prefs.getString("godAct2Text", default.godAct2Text) ?: default.godAct2Text,
            godAct3Title = prefs.getString("godAct3Title", default.godAct3Title) ?: default.godAct3Title,
            godAct3Text = prefs.getString("godAct3Text", default.godAct3Text) ?: default.godAct3Text,
            curseAct1Title = prefs.getString("curseAct1Title", default.curseAct1Title) ?: default.curseAct1Title,
            curseAct1Text = prefs.getString("curseAct1Text", default.curseAct1Text) ?: default.curseAct1Text,
            curseAct2Title = prefs.getString("curseAct2Title", default.curseAct2Title) ?: default.curseAct2Title,
            curseAct2Text = prefs.getString("curseAct2Text", default.curseAct2Text) ?: default.curseAct2Text,
            curseAct3Title = prefs.getString("curseAct3Title", default.curseAct3Title) ?: default.curseAct3Title,
            curseAct3Text = prefs.getString("curseAct3Text", default.curseAct3Text) ?: default.curseAct3Text
        )
    }
}
