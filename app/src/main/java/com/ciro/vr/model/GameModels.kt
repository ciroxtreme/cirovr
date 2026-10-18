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
        godBehavior = "Win Streak Tinggi (Kamu merasa Sakti)",
        curseBehavior = "Loss Streak Near-Miss (Kamu merasa Dikutuk)"
    ),
    PHASE_2_CHAOS(
        stageNumber = 2,
        stageName = "Fase Keraguan (Chaos)",
        godBehavior = "Keraguan & Kejar Kejayaan ('Kok gini? Salah lagi, lagi...')",
        curseBehavior = "Gacha Teknik ('A, B, C nihil, coba lagi berhasil, berhasil...')"
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

data class GameSettings(
    // Putaran Tiap Fase
    val phase1Rounds: Int = 5,
    val phase2Rounds: Int = 5,
    val phase3Rounds: Int = 5,

    // Probabilitas Manipulasi Dadu (%)
    val godPhase1WinRate: Int = 90,
    val godPhase2WinRate: Int = 17, // ~1/6 fair
    val godPhase3WinRate: Int = 5,

    val cursePhase1WinRate: Int = 5,
    val cursePhase2WinRate: Int = 17,
    val cursePhase3WinRate: Int = 90,

    val nearMissRate: Int = 70, // Peluang meleset tipis (+/- 1)

    // Narasi Refleksi Algoritma Dewa
    val godAct1Title: String = "Ilusi Dewa (The God Illusion)",
    val godAct1Text: String = "Di awal, otakmu mengira kamu menemukan pola rahasia semesta. Tebakanmu tembus berkali-kali. Ego berbisik: 'Aku sakti, aku jenius, aku bisa mengendalikan masa depan!' Dopamin membanjiri otakmu.",
    val godAct2Title: String = "Keraguan & Ritual Teknik (The Chasing Phase)",
    val godAct2Text: String = "Kamu merasa bingung kenapa kalah. Kamu pakai teknik yang sama dengan harapan kejayaan sebelumnya kembali lagi:\nUsaha 1 benar\nUsaha berikutnya salah\nUsaha berikut salah lagi\nLagi\nLagi...",
    val godAct3Title: String = "Jebakan (Masuk Dewa, Keluar Budak)",
    val godAct3Text: String = "Tirai terbuka: Kamu sadar bahwa kamu tidak pernah mengontrol apa pun. Kemenangan tadi hanyalah umpan algoritma. Kamu masuk merasa jadi Dewa, dan keluar menjadi budak probabilitas wkwk.",

    // Narasi Refleksi Algoritma Kutukan
    val curseAct1Title: String = "Paranoia ('Semesta Benci Banget')",
    val curseAct1Text: String = "Kamu tebak 5 keluar 4, tebak 2 keluar 3. Kalah meleset tipis berkali-kali! Otakmu panas dan berbisik: 'Anjir, dicurangi! Kenapa aku selalu sial? Semesta kayaknya sengaja nyerang aku hari ini.'",
    val curseAct2Title: String = "Keraguan & Gacha Teknik (False Hope)",
    val curseAct2Text: String = "Pasti pakai teknik A, B, C dan D hasilnya tetap nihil. Saat ngegacha teknik dan mikir 'wah teknik A kayaknya benar':\nAwalnya gagal\nCoba lagi ternyata berhasil\nBerhasil\nBerhasil...",
    val curseAct3Title: String = "Pencerahan (Geer Merasa Dikutuk wkwk)",
    val curseAct3Text: String = "Lalu tiba-tiba kemenangan datang beruntun! Kamu tertawa sadar: Semesta tidak pernah punya dendam kepadamu. Dadu ini tidak punya emosi. Rasa terkutuk itu cuma kegeeran ego yang merasa terlalu penting. Keacakan itu 100% netral!"
)
