package com.seven.learn.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.seven.learn.R
import com.seven.learn.util.Utils

class ResultsActivity : AppCompatActivity() {
    private lateinit var resultsTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var  retryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_results)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultsTextView = findViewById(R.id.resultsTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        retryButton = findViewById(R.id.retryButton)

        val player = Utils.extractPlayerExtra(intent)

        resultsTextView.text = getString(R.string.results_text, player?.name ?: "")
        scoreTextView.text = "${player?.score ?: 0}"
        retryButton.setOnClickListener {
            Intent(this@ResultsActivity, MainActivity::class.java)
                .let {
                    startActivity(it)
                    finish()
                }
        }
    }
}