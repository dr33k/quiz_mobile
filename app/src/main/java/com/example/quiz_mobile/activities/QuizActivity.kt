package com.example.quiz_mobile.activities

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
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var optionButtons: Array<Button>

    private lateinit var checkButton: Button

    private lateinit var countryCodeList: List<String>
    private lateinit var correctAnswerButtonRef: Button
    private var selectedButtonRef: Button? = null

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
        optionButtons = arrayOf(
            findViewById(R.id.optionButton1),
            findViewById(R.id.optionButton2),
            findViewById(R.id.optionButton3),
            findViewById(R.id.optionButton4)
        )

        checkButton = findViewById(R.id.quizCheckButton)

        countryCodeList = Constants.COUNTRY_MAP.keys.toList()

        val qIterator = prepareQuestions(Constants.QUESTION_COUNT).iterator()
        val question = qIterator.next()
        setImage(question)
        setOptions(question)

        checkButton.setOnClickListener {
            optionButtons.forEach { it.isEnabled = false }
            selectedButtonRef?.let{ selected ->
                if(selected.tag == question.correct){
                    selected.setBackgroundColor(getColor(R.color.correct))
                    selected.setTextColor(getColor(R.color.white))
                }else {
                    selected.setBackgroundColor(getColor(R.color.danger))
                    correctAnswerButtonRef.setBackgroundColor(getColor(R.color.correct))
                    correctAnswerButtonRef.setTextColor(getColor(R.color.white))
                }
            }
        }
    }


    fun setImage(question: Question) {
        val inputStream = imageView.context.assets.open(question.image)
        val drawable = Drawable.createFromStream(inputStream, null)
        imageView.setImageDrawable(drawable)
    }

    fun setOptions(question: Question) {
        optionButtons.forEachIndexed { index, optionButton ->
            optionButton.tag = question.options[index]
            optionButton.text = Constants.COUNTRY_MAP[question.options[index]]
            optionButton.setOnClickListener {
                selectedButtonRef?.let { selected ->
                    selected.setBackgroundColor(getColor(R.color.white))
                    selected.setTextColor(getColor(R.color.black))
                }
                correctAnswerButtonRef.let { correct ->
                    correct.setBackgroundColor(getColor(R.color.white))
                    correct.setTextColor(getColor(R.color.black))
                }

                optionButton.setBackgroundColor(getColor(R.color.primary))
                optionButton.setTextColor(getColor(R.color.white))
                selectedButtonRef = optionButton

                checkButton.isEnabled = true
                checkButton.setBackgroundColor(getColor(R.color.primary))
            }

            if(index == question.correct){
                correctAnswerButtonRef = optionButton
            }
        }
    }

    fun prepareQuestions(amount: Int): Array<Question> {
        return Array(amount) { questionsIndex ->
            val correctAnswer = countryCodeList[Random.nextInt(countryCodeList.size)]

            val options = mutableSetOf(correctAnswer) //Add correct answer to set of options
            while (options.size < Constants.OPTION_COUNT) {
                options += countryCodeList[Random.nextInt(countryCodeList.size)]
            }
            val optionsShuffledArray = options.shuffled().toTypedArray()

            Question(
                "images/${correctAnswer.lowercase()}.png",
                optionsShuffledArray,
                optionsShuffledArray.indexOf(correctAnswer)
            )
        }
    }
}