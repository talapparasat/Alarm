package com.example.alarm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao

    companion object {

        private const val DB_NAME = "alarms.db"

        private var instance: AppDatabase? = null

        fun getInstance(context: Context):AppDatabase? {
            if(instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
            }

            return instance
        }
    }
}