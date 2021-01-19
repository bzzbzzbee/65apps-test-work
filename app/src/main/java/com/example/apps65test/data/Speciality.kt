package com.example.apps65test.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apps65test.utilities.SPECIALITY_TABLE_NAME

@Entity(tableName = SPECIALITY_TABLE_NAME)
data class Speciality (
    @PrimaryKey
    val id : Int,

    @ColumnInfo(name = "speciality_name")
    val specialityName :String?
)
