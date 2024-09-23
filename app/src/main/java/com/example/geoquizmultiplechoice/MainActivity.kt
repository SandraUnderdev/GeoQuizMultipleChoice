package com.example.geoquizmultiplechoice

import android.app.Activity
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.geoquizmultiplechoice.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTrue.setOnClickListener { _: View ->
            checkAnswer(true)
            quizViewModel.trackQuestionsAnswered()
        }

        binding.btnFalse.setOnClickListener { view: View ->
            checkAnswer(false)
            quizViewModel.trackQuestionsAnswered()
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            quizViewModel.setVisibility()
            updateQuestion()
        }

        binding.btnNext.setOnClickListener {
            quizViewModel.moveToNext()
            quizViewModel.setVisibility()
            updateQuestion()

        }

        binding.btnPrev.setOnClickListener {
            quizViewModel.moveToPrev()
            quizViewModel.setVisibility() //setVisibility().
            updateQuestion()

        }

        binding.cheatButton.setOnClickListener {
            // val intent = Intent(this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //   startActivity(intent)
            cheatLauncher.launch(intent)
        }

        updateQuestion()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blurCheatButton()
        }

        quizViewModel.toastMessage.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

            }
        })

        quizViewModel.scoreValue.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

            }
        })

        quizViewModel.updateQuestion.observe(this, Observer {
            updateQuestion()
        })

        quizViewModel.buttonVisibility.observe(this, Observer { isVisibility ->
            binding.btnTrue.visibility = if (isVisibility) View.VISIBLE else View.GONE
            binding.btnFalse.visibility = if (isVisibility) View.VISIBLE else View.GONE
        })
    }


    private fun updateQuestion() {
        if (quizViewModel.currentIndex == -1) {
            quizViewModel.currentIndex = quizViewModel.questionBank.size - 1
            val questionTextResId = quizViewModel.currentQuestionText
            binding.questionTextView.setText(questionTextResId)
        } else {
            val questionTextResId = quizViewModel.currentQuestionText
            binding.questionTextView.setText(questionTextResId)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

@RequiresApi(Build.VERSION_CODES.S)
private fun blurCheatButton() {
    val effect = RenderEffect.createBlurEffect(
        10.0f,
        10.0f,
        Shader.TileMode.CLAMP
    )
    binding.cheatButton.setRenderEffect(effect)
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

}