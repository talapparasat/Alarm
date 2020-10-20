package com.example.alarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "alarms"
)

data class Alarm(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name = "status") var status: Boolean
)

//data class Alarm(
//    val name: String,
//    val time: String,
//    val status: Boolean
//)