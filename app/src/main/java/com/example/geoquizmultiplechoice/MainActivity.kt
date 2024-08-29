package com.example.geoquizmultiplechoice

import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquizmultiplechoice.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.btnTrue.setOnClickListener { view: View ->
            checkAnswer(true)
            trackQuestionsAnswered()
        }

        binding.btnFalse.setOnClickListener { view: View ->
            checkAnswer(false)
            trackQuestionsAnswered()
        }

        binding.questionTextView.setOnClickListener {
           // currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.btnNext.setOnClickListener {
         //  currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.btnPrev.setOnClickListener {
          //  currentIndex = (currentIndex - 1) % questionBank.size
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        if (quizViewModel.currentIndex == -1) {
            quizViewModel.currentIndex = quizViewModel.questionBank.size - 1
           // val questionTextResId = questionBank[currentIndex].textResId
            val questionTextResId = quizViewModel.currentQuestionText
            binding.questionTextView.setText(questionTextResId)
        } else {
           // val questionTextResId = questionBank[currentIndex].textResId
            val questionTextResId = quizViewModel.currentQuestionText
            binding.questionTextView.setText(questionTextResId)
        }
    }

    private fun trackQuestionsAnswered() {
        if (currentIndex !in answerList) {
            answerList.add(currentIndex)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
//        val correctAnswer = questionBank[currentIndex].answer

        val correctAnswer = quizViewModel.currentQuestionAnswer

        var messageResId = if (userAnswer == correctAnswer) {
            count++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
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