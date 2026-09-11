package com.seven.learn.util

import android.content.Intent
import android.os.Build
import com.seven.learn.modela.Player

class Utils {
    companion object {
        fun extractPlayerExtra(intent: Intent): Player? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(Constants.PLAYER_KEY, Player::class.java)
            } else {
                intent.getParcelableExtra<Player>(Constants.PLAYER_KEY)
            }
    }
}
