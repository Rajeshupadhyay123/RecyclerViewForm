package com.example.recyclerviewform.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserTable(

    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "firstData")
    var firstData: String = "",

    @ColumnInfo(name = "secondData")
    var secondData: String = ""
)