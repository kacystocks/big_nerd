package com.bignerdranch.android.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import android.widget.TextView

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0
    private var lst = ArrayList<Int>()
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
        lst.clear()
        for (i in questionBank) {
            lst.add(0)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        Log.d(TAG, "Current question index: $currentIndex")
        try {
            val question = questionBank[currentIndex]
        } catch (ex: ArrayIndexOutOfBoundsException) {
            Log.e(TAG,  "Index was out of bounds", ex)
        }
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        if (lst[currentIndex] == 0) {
            var messageResId = 0
            if (userAnswer == correctAnswer) {
                messageResId = R.string.correct_toast
                lst[currentIndex] = 1
            } else {
                messageResId = R.string.incorrect_toast
                lst[currentIndex] = 2
            }
            val toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            toast.setGravity(
                Gravity.BOTTOM, 0, 0
            )
            toast.show()
        } else {
            val toast = Toast.makeText(this, R.string.already_answered, Toast.LENGTH_SHORT)
            toast.setGravity(
                Gravity.BOTTOM, 0, 0
            )
            toast.show()
        }

        // if no 0's in lst, grade quiz
        var tempBool = true
        for (k in lst) {
            if (k == 0){
                tempBool = false
            }
        }

        if (tempBool) {
            // grade quiz
            total = 0
            for (j in lst){
                if (j == 1) {
                    total += 1
                }
            }
            val score = (total.toFloat() / lst.size.toFloat()) * 100
            val sScore = score.toInt().toString()

            // show grade
            val toast = Toast.makeText(this, "Your score is $sScore%", Toast.LENGTH_SHORT)
            toast.setGravity(
                Gravity.TOP, 0, 0
            )
            toast.show()
        }
    }
}
