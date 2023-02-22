package com.example.findoutthisnumber.presentation.game.scores.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findoutthisnumber.presentation.models.ScoreModel
import com.example.findoutthisnumber.presentation.repo.ScoresRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScoresFragmentViewModel(private val repository: ScoresRepository): ViewModel() {

    private var _scoreList = MutableLiveData<List<ScoreModel>>()
    val numberScore: LiveData<List<ScoreModel>>
        get() = _scoreList

     // TODO read from db
    /*fun getScoreList() = viewModelScope.launch{
         repository.getScores().collectLatest {
             _scoreList.value = it
        }
    }*/

}