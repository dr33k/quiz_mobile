package com.example.quiz_mobile.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quiz_mobile.R
import com.example.quiz_mobile.modela.Question
import com.example.quiz_mobile.util.Constants
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var optionButton1: Button
    private lateinit var optionButton2: Button
    private lateinit var optionButton3: Button
    private lateinit var optionButton4: Button
    private lateinit var countryCodeList: List<String>
    private lateinit var checkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
        optionButton1 = findViewById(R.id.optionButton1)
        optionButton2 = findViewById(R.id.optionButton2)
        optionButton3 = findViewById(R.id.optionButton3)
        optionButton4 = findViewById(R.id.optionButton4)
        checkButton = findViewById(R.id.quizCheckButton)


        countryCodeList = Constants.COUNTRY_MAP.keys.toList()

    }

    fun prepareQuestion(): Question {
        val countryIndex = Random.nextInt(countryCodeList.size)
        val countryCode = countryCodeList[countryIndex]

        val optionIndexes = mutableSetOf(countryIndex) //Add correct answer to set of options

        while(optionIndexes.size < Constants.OPTION_COUNT){
            optionIndexes += Random.nextInt(countryCodeList.size)
        }

        val options: Array<String> = optionIndexes.map { countryCodeList[it] }.toTypedArray()

        return Question(
            "${countryCode.lowercase()}.png",
            options,
            countryCode
        )
    }
}