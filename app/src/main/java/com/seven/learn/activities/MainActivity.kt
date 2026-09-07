package com.seven.learn.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.seven.learn.R
import com.seven.learn.util.Constants

class MainActivity : AppCompatActivity() {
    private lateinit var namePromptTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var startButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        namePromptTextView = findViewById(R.id.namePromptTextView)
        nameEditText = findViewById(R.id.nameEditText)
        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            namePromptTextView.text = getString(R.string.name_prompt_text)
            namePromptTextView.setTextColor(Color.BLACK)

            val playerName = nameEditText.text.toString()

            if(playerName.isBlank()){
                namePromptTextView.text = getString(R.string.empty_input_error_text)
                namePromptTextView.setTextColor(Color.RED)
                return@setOnClickListener
            }

            Intent(this@MainActivity, QuizActivity::class.java).apply{
                with(this){
                    putExtra(Constants.PLAYER_NAME_KEY, playerName)
                }
            }.also { startActivity(it) }
        }
    }
}