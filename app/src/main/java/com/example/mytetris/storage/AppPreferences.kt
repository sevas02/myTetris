package com.example.mytetris.storage

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    var data: SharedPreferences = context.getSharedPreferences(
        "APP_PREFERENCES", Context.MODE_PRIVATE)

    fun saveHighScore(score: Int) = data.edit().putInt("HIGH_SCORE", score).apply()

    fun getHighScore() = data.getInt("HIGH_SCORE", 0)

    fun cleanHighScore() = data.edit().putInt("HIGH_SCORE", 0).apply()
}