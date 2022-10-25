package com.example.testwmandam.room.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testwmandam.model.TimeModel
import com.example.testwmandam.room.dao.TimeDao

@Database(
    entities =
    [TimeModel::class],
    exportSchema = false,
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract fun timeDao(): TimeDao

    companion object {
        private const val DATABASE_NAME = "TEST_TIME"
        private var instance: RoomDB? = null
        fun getInstance(application: Application): RoomDB {
            synchronized(RoomDB::class.java) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        application,
                        RoomDB::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return instance!!
        }
    }
}