package com.example.findoutthisnumber.data.repository

import android.content.Context
import com.example.findoutthisnumber.data.room.database.LocalDB
import com.example.findoutthisnumber.data.room.entity.toScoreModel
import com.example.findoutthisnumber.data.room.entity.toScoreSingleModel
import com.example.findoutthisnumber.presentation.models.ScoreModel
import com.example.findoutthisnumber.presentation.models.toScoreEntity
import com.example.findoutthisnumber.presentation.models.toScoreSingleEntity
import com.example.findoutthisnumber.presentation.repo.ScoresRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ScoresRepositoryImpl(
    private val context: Context
    ): ScoresRepository {

    var roomDB = LocalDB.getInstance(context)?.localDataDao()

     //Todo read from DB
    /*override suspend fun getScores(): Flow<List<ScoreModel>> = flow {
        val returnData = roomDB!!.getAllScores().map {
            it.toScoreSingleModel()
        }

        emit(returnData)
    }.flowOn(Dispatchers.IO)*/


    override suspend fun insertScores(score: ScoreModel) {
        roomDB!!.insertScore(score.toScoreSingleEntity())
    }
}