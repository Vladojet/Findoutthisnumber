package com.example.findoutthisnumber.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.findoutthisnumber.data.room.entity.ScoreEntity

@Dao
interface ScoresDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(scoreEntity: ScoreEntity)

    //TODO("read form db")
    /*@Query("SELECT * FROM episodes_table")
    suspend fun getAllScores(): List<ScoreEntity>*/

}