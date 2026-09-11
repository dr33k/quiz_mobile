package com.seven.learn.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.seven.learn.R
import com.seven.learn.fragments.BottomSheet
import com.seven.learn.modela.Player
import com.seven.learn.modela.Question
import com.seven.learn.util.Constants
import com.seven.learn.util.Utils
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var optionButtons: Array<Button>

    private lateinit var checkButton: Button

    private lateinit var countryCodeList: List<String>
    private lateinit var correctAnswerButtonRef: Button
    private var selectedButtonRef: Button? = null

    private var player: Player? = null

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

        player = Utils.extractPlayerExtra(intent)

        countryCodeList = Constants.COUNTRY_MAP.keys.toList()

        val qIterator = prepareQuestions(Constants.QUESTION_COUNT).iterator()
        var question = displayNextQuestion(qIterator)

        checkButton.setOnClickListener {
            //Disable all buttons
            optionButtons.forEach { it.isEnabled = false }
            checkButton.isEnabled = false
            checkButton.setBackgroundColor(getColor(R.color.grey))

            val isCorrectAnswer = selectedButtonRef?.tag == question.options[question.correct]
            //Color selections
            selectedButtonRef?.let {
                if (isCorrectAnswer) {//Correct
                    it.setBackgroundColor(getColor(R.color.correct))
                    it.setTextColor(getColor(R.color.white))
                } else {//Wrong
                    it.setBackgroundColor(getColor(R.color.danger))
                    it.setTextColor(getColor(R.color.white))
                    correctAnswerButtonRef.setBackgroundColor(getColor(R.color.correct))
                    correctAnswerButtonRef.setTextColor(getColor(R.color.white))
                }
            }
            //Increment progress bar
            progressBar.incrementProgressBy(100 / Constants.QUESTION_COUNT)

            //Increment score
            player?.let { it.score += if(isCorrectAnswer) 1 else 0}

            BottomSheet.newInstance(isCorrectAnswer){
                if(qIterator.hasNext()){
                    question = displayNextQuestion(qIterator)
                } else{
                    Intent(this@QuizActivity, ResultsActivity::class.java).apply {
                        putExtras(intent)
                    }.let {
                        startActivity(it)
                        finish()
                    }
                }
            }.show(supportFragmentManager, "")
        }
    }


    private fun displayNextQuestion(qIterator: Iterator<Question>): Question{
        val question = qIterator.next()
        setImage(question)
        setOptions(question)
        return question
    }

    private fun setImage(question: Question) {
        val inputStream = imageView.context.assets.open(question.image)
        val drawable = Drawable.createFromStream(inputStream, null)
        imageView.setImageDrawable(drawable)
    }

    private fun setOptions(question: Question) {
        optionButtons.forEachIndexed { index, optionButton ->
            optionButton.isEnabled = true
            optionButton.tag = question.options[index]
            optionButton.text = Constants.COUNTRY_MAP[question.options[index]]
            optionButton.setBackgroundColor(getColor(R.color.white))
            optionButton.setTextColor(getColor(R.color.black))

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
                checkButton.setTextColor(getColor(R.color.white))
            }

            if (index == question.correct) {
                correctAnswerButtonRef = optionButton
            }
        }
    }

    private fun prepareQuestions(amount: Int): Array<Question> =
        Array(amount) {
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