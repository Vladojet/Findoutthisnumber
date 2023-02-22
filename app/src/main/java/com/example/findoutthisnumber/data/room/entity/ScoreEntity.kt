package com.example.findoutthisnumber.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.findoutthisnumber.presentation.models.ScoreModel

@Entity(tableName = "score_table")

data class ScoreEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val matchResult: String,
    val userNumber: Int
)

fun ScoreEntity.toScoreModel(): List<ScoreModel> {
    val listScoreModel = mutableListOf<ScoreModel>()

    var list = listOf<ScoreEntity>().forEach {
        listScoreModel.add(
            ScoreModel(
                it.id,
                it.matchResult,
                it.userNumber
            )
        )
    }
    return listScoreModel
}

fun ScoreEntity.toScoreSingleModel(): ScoreModel {
    return ScoreModel(
        this.id,
        this.matchResult,
        this.userNumber
    )
}


