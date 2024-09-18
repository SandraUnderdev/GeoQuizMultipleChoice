package com.example.geoquizmultiplechoice

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizViewModelTest {
    @Test
    fun providesExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun wrapsAroundQuestionText() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun QuestionAnswerTrueorFalse() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertTrue(true.toString(), quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        assertTrue(true.toString(), quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        assertFalse(false.toString(), quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        assertFalse(false.toString(), quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        assertTrue(true.toString(), quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        assertTrue(true.toString(), quizViewModel.currentQuestionAnswer)
    }
}