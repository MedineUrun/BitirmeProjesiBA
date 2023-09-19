package com.example.bitirmeprojesi

import android.app.Activity
import android.util.DisplayMetrics

fun Activity.windowHeight(): Int  {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}