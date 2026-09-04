package com.example.quiz_mobile.activities

import android.content.Context
import android.graphics.drawable.Drawable
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
import java.time.temporal.TemporalAmount
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var optionButton1: Button
    private lateinit var optionButton2: Button
    private lateinit var optionButton3: Button
    private lateinit var optionButton4: Button
    private lateinit var checkButton: Button

    private lateinit var countryCodeList: List<String>
    private var selectedButton: Button? = null

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

        val questions = prepareQuestions(Constants.QUESTION_COUNT)
        setImage(questions[0])
        setOptions(questions[0])
    }


    fun setImage(question: Question) {
        val inputStream = imageView.context.assets.open(question.image)
        val drawable = Drawable.createFromStream(inputStream, null)
        imageView.setImageDrawable(drawable)
    }

    fun setOptions(question: Question) {
        val buttons = listOf(optionButton1, optionButton2, optionButton3, optionButton4)
        buttons.forEachIndexed { index, button ->
            button.tag = question.options[index]
            button.text = Constants.COUNTRY_MAP[question.options[index]]
            button.setOnClickListener {
                selectedButton?.let { selected ->
                    selected.setBackgroundColor(getColor(R.color.white))
                    selected.setTextColor(getColor(R.color.black))
                }
                button.setBackgroundColor(getColor(R.color.primary))
                button.setTextColor(getColor(R.color.white))
                selectedButton = button
            }
        }
    }

    fun prepareQuestions(amount: Int): Array<Question> {
        return Array(amount) { index ->
            val countryIndex = Random.nextInt(countryCodeList.size)
            val countryCode = countryCodeList[countryIndex]

            val options = mutableSetOf(countryCode) //Add correct answer to set of options
            while (options.size < Constants.OPTION_COUNT) {
                options += countryCodeList[Random.nextInt(countryCodeList.size)]
            }

            Question(
                "images/${countryCode.lowercase()}.png",
                options.toTypedArray(),
                countryCode
            )
        }
    }
}