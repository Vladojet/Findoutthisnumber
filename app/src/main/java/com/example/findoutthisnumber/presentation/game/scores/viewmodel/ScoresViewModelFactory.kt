package com.example.findoutthisnumber.presentation.game.scores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.findoutthisnumber.presentation.repo.ScoresRepository

class ScoresViewModelFactory(private val repo: ScoresRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoresFragmentViewModel::class.java)) {
            return ScoresFragmentViewModel(repo) as T
        }
        throw java.lang.IllegalArgumentException("it is empty")
    }
}