package com.example.findoutthisnumber.presentation.game.minigame


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findoutthisnumber.presentation.models.ScoreModel
import com.example.findoutthisnumber.presentation.repo.ScoresRepository
import com.example.findoutthisnumber.presentation.utils.ActionState
import com.example.findoutthisnumber.presentation.utils.GameState
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameFragmentViewModel(private val repository: ScoresRepository) : ViewModel() {


    private var systemDesiredNumber: Int = 1

    init {
        systemDesiredNumber = initDesireNumber()
    }

    private fun initDesireNumber(): Int {
        return Random.nextInt(1, 100)
    }


    private var _numberScore = MutableLiveData(1)
    val numberScore: LiveData<Int>
        get() = _numberScore

    private var _actionState = MutableLiveData(ActionState.INCREASING)
    val actionState: LiveData<ActionState>
        get() = _actionState

    private var _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState>
        get() = _gameState

    private var _desiredNumber = MutableLiveData(systemDesiredNumber)
    val desiredNumber: LiveData<Int>
        get() = _desiredNumber

    fun incByOne() {
        if (inAdditionRange(1)) {
            _numberScore.value = _numberScore.value?.inc()
        } else _numberScore.value
    }

    fun incByFive() {
        if (inAdditionRange(5)) {
        _numberScore.value = _numberScore.value?.plus(5)
    } else _numberScore.value
    }

    fun incByTen() {
        if (inAdditionRange(10)) {
            _numberScore.value = _numberScore.value?.plus(10)
        } else _numberScore.value

    }

    fun incByTwenty() {
        if (inAdditionRange(20)) {
            _numberScore.value = _numberScore.value?.plus(20)
        } else _numberScore.value
    }

    fun decByOne() {
        if (inDecreasingRange(1)) {
            _numberScore.value = _numberScore.value?.dec()
        } else _numberScore.value
    }

    fun decByFive() {
        if (inDecreasingRange(5)) {
            _numberScore.value = _numberScore.value?.minus(5)
        } else _numberScore.value
    }

    fun decByTen() {
        if (inDecreasingRange(10)) {
            _numberScore.value = _numberScore.value?.minus(10)
        } else _numberScore.value

    }

    fun decByTwenty() {
        if (inDecreasingRange(20)) {
            _numberScore.value = _numberScore.value?.minus(20)
        } else _numberScore.value
    }

    fun changeSign() {
        if (_actionState.value == ActionState.INCREASING) {
            _actionState.value = ActionState.DECREASING
        } else _actionState.value = ActionState.INCREASING
    }

    fun compareNumbers() {
        if (_desiredNumber.value == _numberScore.value) {
            _gameState.value = GameState.WINING
            insertInDatabase("You win", _numberScore.value!!)
        } else {
            _gameState.value = GameState.LOOSING
            insertInDatabase("You loose", _numberScore.value!!)
        }
    }

    fun restartGame() {
        initDesireNumber()
        _desiredNumber.value = initDesireNumber()
        _numberScore.value = 1
    }

    private fun insertInDatabase(gameResult: String, userNumber: Int) = viewModelScope.launch {
        repository.insertScores(ScoreModel(matchResult = gameResult, userNumber = userNumber))
    }

    private fun inAdditionRange(actionNumber: Int): Boolean {
        return if (_numberScore.value?.plus(actionNumber) in 1..100) {
            return true
        } else false
    }

    private fun inDecreasingRange(actionNumber: Int): Boolean {
        return if (_numberScore.value?.minus(actionNumber) in 1..100) {
            return true
        } else false
    }
}

