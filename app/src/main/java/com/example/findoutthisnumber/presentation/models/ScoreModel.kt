package com.example.findoutthisnumber.presentation.models

import com.example.findoutthisnumber.data.room.entity.ScoreEntity

data class ScoreModel(
    val gameId: Int = 1,
    val matchResult: String,
    val userNumber: Int
) {
    fun toScoreEntity(): ScoreEntity {
        return ScoreEntity(
            this.gameId,
            this.matchResult,
            this.userNumber
        )
    }
}

fun ScoreModel.toScoreEntity(): List<ScoreEntity> {
    val listScoreModel = mutableListOf<ScoreEntity>()

    var list = listOf<ScoreEntity>().forEach {
        listScoreModel.add(
            ScoreEntity(
                it.id,
                it.matchResult,
                it.userNumber
            )
        )
    }
    return listScoreModel
}

fun ScoreModel.toScoreSingleEntity(): ScoreEntity {
    return ScoreEntity(
        this.gameId,
        this.matchResult,
        this.userNumber
    )
}

