package com.example.alarm

import androidx.room.*

@Dao
interface AlarmDao {

    @Insert
    fun insertAlarm(alarm: Alarm)

    @Query("SELECT * FROM alarms")
    fun getAlarms(): List<Alarm>

    @Query("UPDATE alarms SET status=:switchOn WHERE id=:id")
    fun switchStatus(id: Int, switchOn: Boolean = true)

//    @Query("DELETE FROM items WHERE id=:id")
//    fun deleteItemById(id: Int)

    @Delete
    fun delete(alarm: Alarm)

}