package com.example.mytetris

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mytetris.databinding.ActivityGameBinding
import com.example.mytetris.models.AppModel
import com.example.mytetris.storage.AppPreferences
import com.example.mytetris.view.TetrisView

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    lateinit var tvHighScoreValueGame: TextView
    lateinit var tvCurrentScoreValue: TextView
    lateinit var btnRestart: Button
    private lateinit var tetrisView: TetrisView

    lateinit var preferences: AppPreferences
    private var appModel: AppModel = AppModel()

    public override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Game activity", "start game activity")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preferences = AppPreferences(this@GameActivity)
        appModel.setPreferences(preferences)


        tvHighScoreValueGame = binding.tvHighScoreValueGame
        tvCurrentScoreValue = binding.tvCurrentScoreValue
        btnRestart = binding.btnRestart
        tetrisView = binding.tetrisView

        tetrisView.setActivity(this@GameActivity)
        tetrisView.setModel(appModel)

        tetrisView.setOnTouchListener(this::onTetrisViewTouch)
        btnRestart.setOnClickListener { appModel.restartGame() }
        updateHighScore()
        updateCurrentScore()
    }

    private fun onTetrisViewTouch(view: View, event: MotionEvent): Boolean {
        if (appModel.isGameOver() || appModel.isGameAwaitingStart()) {
            appModel.startGame()
            tetrisView.setGameCommandWithDelay(AppModel.Motions.DOWN)
        } else if (appModel.isGameActive()) {
            when (resolveTouchDirection(view, event)) {
                0 -> moveTetramino(AppModel.Motions.LEFT)
                1 -> moveTetramino(AppModel.Motions.ROTATE)
                2 -> moveTetramino(AppModel.Motions.DOWN)
                3 -> moveTetramino(AppModel.Motions.RIGHT)
            }
        }
        return true
    }

    private fun resolveTouchDirection(view: View, event: MotionEvent): Int {
        val x = event.x / view.width
        val y = event.y / view.height
        val direction: Int = if (y > x) {
            if (x > 1 - y) 2 else 0
        } else {
            if (x > 1 - y) 3 else 1
        }
        return direction
    }

    private fun moveTetramino(motion: AppModel.Motions){
        Log.d("move", "ban")
        if (appModel.isGameActive()) {
            tetrisView.setGameCommand(motion)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateHighScore() {
        tvHighScoreValueGame.text = preferences.getHighScore().toString()
    }

    private fun updateCurrentScore() {
        tvCurrentScoreValue.text = "0"
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, GameActivity::class.java)
    }

}