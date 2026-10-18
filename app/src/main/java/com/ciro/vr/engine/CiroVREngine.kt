package com.ciro.vr.engine

import com.ciro.vr.model.AlgorithmType
import com.ciro.vr.model.GameMode
import com.ciro.vr.model.Phase
import com.ciro.vr.model.RollHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs
import kotlin.random.Random

data class GameState(
    val gameMode: GameMode = GameMode.BLIND,
    val secretAlgorithm: AlgorithmType = AlgorithmType.GOD,
    val selectedConsciousAlgorithm: AlgorithmType = AlgorithmType.GOD,
    val currentRound: Int = 1,
    val selectedGuess: Int = 1,
    val lastRolledNumber: Int = 1,
    val isRolling: Boolean = false,
    val history: List<RollHistory> = emptyList(),
    val currentStreak: Int = 0, // Positif = win streak, negatif = loss streak
    val maxWinStreak: Int = 0,
    val maxLossStreak: Int = 0,
    val isDebriefVisible: Boolean = false,
    val totalWins: Int = 0,
    val totalLosses: Int = 0
) {
    val activeAlgorithm: AlgorithmType
        get() = if (gameMode == GameMode.BLIND) secretAlgorithm else selectedConsciousAlgorithm

    val currentPhase: Phase
        get() = when {
            currentRound <= 6 -> Phase.PHASE_1_BAIT
            currentRound <= 12 -> Phase.PHASE_2_CHAOS
            else -> Phase.PHASE_3_REALITY
        }
}

class CiroVREngine {

    private val _state = MutableStateFlow(
        GameState(secretAlgorithm = if (Random.nextBoolean()) AlgorithmType.GOD else AlgorithmType.CURSE)
    )
    val state: StateFlow<GameState> = _state.asStateFlow()

    fun setGameMode(mode: GameMode) {
        _state.update {
            it.copy(
                gameMode = mode,
                isDebriefVisible = false
            )
        }
    }

    fun setConsciousAlgorithm(algorithmType: AlgorithmType) {
        _state.update {
            it.copy(selectedConsciousAlgorithm = algorithmType)
        }
    }

    fun setGuess(number: Int) {
        if (number in 1..6) {
            _state.update { it.copy(selectedGuess = number) }
        }
    }

    fun startRoll() {
        _state.update { it.copy(isRolling = true) }
    }

    fun completeRoll(guess: Int): RollHistory {
        val currentState = _state.value
        val algo = currentState.activeAlgorithm
        val phase = currentState.currentPhase

        val actualDice = calculateDiceOutcome(guess, algo, phase)
        val isWin = (actualDice == guess)
        val isNearMiss = !isWin && abs(actualDice - guess) == 1

        val newStreak = if (isWin) {
            if (currentState.currentStreak > 0) currentState.currentStreak + 1 else 1
        } else {
            if (currentState.currentStreak < 0) currentState.currentStreak - 1 else -1
        }

        val newMaxWin = if (isWin && newStreak > currentState.maxWinStreak) newStreak else currentState.maxWinStreak
        val newMaxLoss = if (!isWin && abs(newStreak) > currentState.maxLossStreak) abs(newStreak) else currentState.maxLossStreak

        val historyItem = RollHistory(
            round = currentState.currentRound,
            guess = guess,
            actual = actualDice,
            isWin = isWin,
            phase = phase,
            algorithm = algo,
            isNearMiss = isNearMiss
        )

        _state.update {
            it.copy(
                isRolling = false,
                lastRolledNumber = actualDice,
                currentRound = it.currentRound + 1,
                currentStreak = newStreak,
                maxWinStreak = newMaxWin,
                maxLossStreak = newMaxLoss,
                totalWins = if (isWin) it.totalWins + 1 else it.totalWins,
                totalLosses = if (!isWin) it.totalLosses + 1 else it.totalLosses,
                history = listOf(historyItem) + it.history
            )
        }

        return historyItem
    }

    fun showDebrief() {
        _state.update { it.copy(isDebriefVisible = true) }
    }

    fun hideDebrief() {
        _state.update { it.copy(isDebriefVisible = false) }
    }

    fun resetGame() {
        val nextSecret = if (Random.nextBoolean()) AlgorithmType.GOD else AlgorithmType.CURSE
        _state.update {
            GameState(
                gameMode = it.gameMode,
                secretAlgorithm = nextSecret,
                selectedConsciousAlgorithm = it.selectedConsciousAlgorithm,
                selectedGuess = it.selectedGuess
            )
        }
    }

    private fun calculateDiceOutcome(guess: Int, algorithm: AlgorithmType, phase: Phase): Int {
        return when (algorithm) {
            AlgorithmType.GOD -> {
                when (phase) {
                    Phase.PHASE_1_BAIT -> {
                        // 90% peluang menang berturut-turut!
                        if (Random.nextInt(100) < 90) guess else getDifferentNumber(guess)
                    }
                    Phase.PHASE_2_CHAOS -> {
                        // Acak murni 1/6 (kebingungan "Kok bisa?")
                        Random.nextInt(1, 7)
                    }
                    Phase.PHASE_3_REALITY -> {
                        // 95% peluang kalah (dihantam realita, masuk dewa keluar budak)
                        if (Random.nextInt(100) < 5) guess else getNearMissOrDifferent(guess)
                    }
                }
            }
            AlgorithmType.CURSE -> {
                when (phase) {
                    Phase.PHASE_1_BAIT -> {
                        // 95% peluang kalah meleset tipis (merasa sial / dikutuk)
                        if (Random.nextInt(100) < 5) guess else getNearMissOrDifferent(guess)
                    }
                    Phase.PHASE_2_CHAOS -> {
                        // Acak murni 1/6 (mulai ada titik balik)
                        Random.nextInt(1, 7)
                    }
                    Phase.PHASE_3_REALITY -> {
                        // 90% peluang menang berturut-turut (pencerahan, rasa bebas)
                        if (Random.nextInt(100) < 90) guess else getDifferentNumber(guess)
                    }
                }
            }
            AlgorithmType.FAIR -> {
                // Acak murni 1..6
                Random.nextInt(1, 7)
            }
        }
    }

    private fun getDifferentNumber(exclude: Int): Int {
        val candidates = (1..6).filter { it != exclude }
        return candidates.random()
    }

    private fun getNearMissOrDifferent(guess: Int): Int {
        // Prioritaskan angka selisih 1 (near miss) untuk memicu respons emosi kuat
        val nearMisses = listOf(guess - 1, guess + 1).filter { it in 1..6 }
        return if (nearMisses.isNotEmpty() && Random.nextInt(100) < 70) {
            nearMisses.random()
        } else {
            getDifferentNumber(guess)
        }
    }
}
