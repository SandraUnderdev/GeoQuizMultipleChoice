package com.example.geoquizmultiplechoice

import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquizmultiplechoice.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var count = 0
    private var answerList = mutableListOf<Int>()
    private var score: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // binding.btnTrue.setOnClickListener { view: View ->
        binding.btnTrue.setOnClickListener { _: View ->
            checkAnswer(true)
            trackQuestionsAnswered()
        }

        binding.btnFalse.setOnClickListener { view: View ->
            checkAnswer(false)
            trackQuestionsAnswered()
        }

        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.btnNext.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            setVisibility()
        }

        binding.btnPrev.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            setVisibility()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        if (currentIndex == -1) {
            // can't find tag
            Log.d(TAG, "Current question index:  $currentIndex")
//            try { val question = questionBank[currentIndex]
//            } catch (ex: ArrayIndexOutOfBoundsException){ Log.e(TAG, "Sandra Index was out of bounds", ex) }
            currentIndex = questionBank.size - 1
            val questionTextResId = questionBank[currentIndex].textResId
            binding.questionTextView.setText(questionTextResId)
        } else {
            val questionTextResId = questionBank[currentIndex].textResId
            binding.questionTextView.setText(questionTextResId)
        }
    }

    private fun trackQuestionsAnswered() {
        if (currentIndex !in answerList) {
            answerList.add(currentIndex)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        var messageResId = if (userAnswer == correctAnswer) {
            count++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }


    private fun setVisibility() {
        if (answerList.size == 6) {
            binding.btnTrue.visibility = View.GONE
            binding.btnFalse.visibility = View.GONE
            score = (count * 100) / answerList.size
            Toast.makeText(this, "All 6 Questions Completed", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Your score is $score %", Toast.LENGTH_SHORT).show()
        } else if (currentIndex in answerList) {
            binding.btnTrue.visibility = View.GONE
            binding.btnFalse.visibility = View.GONE
            updateQuestion()
        } else {
            binding.btnTrue.visibility = View.VISIBLE
            binding.btnFalse.visibility = View.VISIBLE
            updateQuestion()
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

    //not called
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

}