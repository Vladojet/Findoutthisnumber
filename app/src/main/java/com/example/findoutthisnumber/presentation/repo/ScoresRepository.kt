package com.example.findoutthisnumber.presentation.repo

import com.example.findoutthisnumber.presentation.models.ScoreModel
import kotlinx.coroutines.flow.Flow

interface ScoresRepository {

      //TODO read from db
      /*suspend fun getScores(): Flow<List<ScoreModel>>*/

      suspend fun insertScores(score: ScoreModel)
}