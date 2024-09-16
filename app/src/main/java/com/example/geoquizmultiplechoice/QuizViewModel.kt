package com.example.geoquizmultiplechoice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var answerList = mutableListOf<Int>()
    private var count = 0
    private var isButtonVisible = true
    private var score: Int? = null

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private val _scoreValue = MutableLiveData<String>()
    val scoreValue: LiveData<String> get() = _scoreValue

    private val _updateQuestion = MutableLiveData<Unit>()
    val updateQuestion: LiveData<Unit> get() = _updateQuestion

    private val _buttonVisibility = MutableLiveData<Boolean>()
    val buttonVisibility: LiveData<Boolean> get() = _buttonVisibility

    // to understand view model life cycle
    init {
        Log.d(TAG, "viewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "viewModel instance about to be destroyed")
    }

    internal val questionBank = listOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true)
    )


    internal var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun trackQuestionsAnswered() {
        if (currentIndex !in answerList) {
            answerList.add(currentIndex)
        }
    }

    fun setVisibility() {
        val message: String
        val showScore: String

        if (answerList.size == 6) {
            score = (count * 100) / answerList.size
            message = "All 6 Questions Completed"
            showScore = "Your score is $score %"
            _toastMessage.value = message
            _scoreValue.value = showScore
            _buttonVisibility.value = false
        } else if (currentIndex in answerList) {
            _buttonVisibility.value = false
            _updateQuestion.value = Unit

        } else {
            _buttonVisibility.value = true
            _updateQuestion.value = Unit
        }
    }

    fun updateCorrectCount(correctAnswer: Boolean) {
        if (correctAnswer) {
            count++
        }
    }
}

