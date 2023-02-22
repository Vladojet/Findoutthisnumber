package com.example.findoutthisnumber.presentation.game.minigame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.findoutthisnumber.presentation.repo.ScoresRepository

class GameViewModelFactory(private val repo: ScoresRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameFragmentViewModel::class.java)){
            return GameFragmentViewModel(repo) as T
        }
        throw java.lang.IllegalArgumentException("it is empty")
    }
}