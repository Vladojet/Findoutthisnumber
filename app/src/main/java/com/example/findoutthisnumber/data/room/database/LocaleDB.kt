package com.example.findoutthisnumber.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.findoutthisnumber.data.room.dao.ScoresDAO
import com.example.findoutthisnumber.data.room.entity.ScoreEntity

@Database(entities = [ScoreEntity::class], version = 1, exportSchema = false)
@TypeConverters

abstract class LocalDB : RoomDatabase() {

    abstract fun localDataDao(): ScoresDAO

    companion object {

        private var instance: LocalDB? = null

        fun getInstance(context: Context): LocalDB? {
            if (instance == null) {
                synchronized(LocalDB::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDB::class.java,
                        "localBD.db"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}