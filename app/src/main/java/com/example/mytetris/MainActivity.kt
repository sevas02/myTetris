package com.example.mytetris

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mytetris.databinding.ActivityMainBinding
import com.example.mytetris.storage.AppPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: AppPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()
        preferences = AppPreferences(this@MainActivity)

        val btnExit = binding.bntExit
        val btnNewGame = binding.btnNewGame
        val btnResetScore = binding.btnResetScore

        val tvHightScoreValue = binding.tvHighScoreValue

        tvHightScoreValue.text = preferences.getHighScore().toString()

        btnExit.setOnClickListener {
            finish()
        }

        btnResetScore.setOnClickListener {
            preferences.cleanHighScore()
            tvHightScoreValue.text = "0"
            Toast.makeText(this, "High score is reset!", Toast.LENGTH_SHORT).show()
        }

        btnNewGame.setOnClickListener {
            startActivity(GameActivity.newIntent(this@MainActivity))
        }
    }
}