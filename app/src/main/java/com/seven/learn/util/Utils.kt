package com.seven.learn.util

import android.content.Intent
import android.os.Build
import android.text.InputFilter
import com.seven.learn.modela.Player

class Utils {
    companion object {
         val NAME_INPUT_FILTER = InputFilter{ source, start, end, dest, dstart, dend ->
             //Allow letters, spaces, hyphens and apostrophes
             val regex = Regex("^[a-zA-Z\\s'-]+$")
             for(i in start until end){
                 if(!source[i].toString().matches(regex)){
                     return@InputFilter ""
                 }
             }
            null
        }
        fun extractPlayerExtra(intent: Intent): Player? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(Constants.PLAYER_KEY, Player::class.java)
            } else {
                intent.getParcelableExtra<Player>(Constants.PLAYER_KEY)
            }
    }
}
