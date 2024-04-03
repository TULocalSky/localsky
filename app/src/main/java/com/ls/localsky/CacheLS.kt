package com.ls.localsky

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ls.localsky.models.WeatherData

class CacheLS {


}



@Entity(tableName = "LocalSky")
data class Result(
    @PrimaryKey()
    val userID: String,
    @ColumnInfo
    val lastWeatherData: WeatherData
)