package com.example.testwmandam.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_list")
data class TimeModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "hour")
    var hour: Int = 0,
    @ColumnInfo(name = "minute")
    var minute: Int = 0
)
