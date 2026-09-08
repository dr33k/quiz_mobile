package com.seven.learn.modela

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player (val name: String, var score: Int = 0): Parcelable