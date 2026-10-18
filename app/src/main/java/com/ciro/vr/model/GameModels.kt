package com.ciro.vr.model

enum class GameMode {
    BLIND,       // Mode Uji Buta: Pemain tidak tahu algoritma rahasia
    CONSCIOUS    // Mode Sadar: Pemain secara sadar memilih algoritma
}

enum class AlgorithmType(
    val title: String,
    val subtitle: String,
    val description: String
) {
    GOD(
        title = "Algoritma Dewa",
        subtitle = "Ilusi Kendali (Illusion of Control)",
        description = "Sistem sengaja membuatmu menang beruntun agar egomu merasa mengendalikan masa depan, lalu menjatuhkanmu ke jurang kekalahan beruntun."
    ),
    CURSE(
        title = "Algoritma Kutukan",
        subtitle = "Ketakberdayaan Terpelajari (Learned Helplessness)",
        description = "Sistem sengaja membuatmu kalah meleset tipis berulang-ulang agar kamu merasa dikutuk dan jadi korban semesta, lalu membalikkan keadaan dengan kemenangan mengejutkan."
    ),
    FAIR(
        title = "Probabilitas Netral",
        subtitle = "Acak Murni (1/6 Rasio Adil)",
        description = "Sistem murni melempar dadu tanpa manipulasi. Bukti nyata bahwa keacakan itu dingin, adil, dan tidak memiliki dendam pribadi pada siapa pun."
    )
}

enum class Phase(
    val stageNumber: Int,
    val stageName: String,
    val godBehavior: String,
    val curseBehavior: String
) {
    PHASE_1_BAIT(
        stageNumber = 1,
        stageName = "Fase Umpan (Bait)",
        godBehavior = "Win Streak 90%+ (Kamu merasa Sakti)",
        curseBehavior = "Loss Streak Near-Miss (Kamu merasa Dikutuk)"
    ),
    PHASE_2_CHAOS(
        stageNumber = 2,
        stageName = "Fase Keraguan (Chaos)",
        godBehavior = "Acak Adil 1/6 ('Kok bisa? Kok gini?')",
        curseBehavior = "Acak Adil 1/6 (Titik Balik Keraguan)"
    ),
    PHASE_3_REALITY(
        stageNumber = 3,
        stageName = "Fase Realita (Crash / Awakening)",
        godBehavior = "Loss Streak Telak (Masuk Dewa, Keluar Budak)",
        curseBehavior = "Win Streak Kejutan (Bebas dari Rasa Jadi Korban)"
    )
}

data class RollHistory(
    val round: Int,
    val guess: Int,
    val actual: Int,
    val isWin: Boolean,
    val phase: Phase,
    val algorithm: AlgorithmType,
    val isNearMiss: Boolean = false
)
