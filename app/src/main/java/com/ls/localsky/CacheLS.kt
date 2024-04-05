package com.ls.localsky

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ls.localsky.models.WeatherData


class CacheLS(
    val context: Context
) {
    val cache = Room.databaseBuilder(
        context,
        CacheDB::class.java, "LocalSkyDatabase"
    ).build()

    fun getCachedWeatherData(userID: String): WeatherData{
        return cache.weatherDataDao().findByUserID(userID).lastWeatherData
    }

    fun updateCachedWeatherData(userID: String, weatherData: WeatherData){
        cache.weatherDataDao().insert(UserWeatherData(userID, weatherData))
    }

}

@Database(entities = [UserWeatherData::class], version = 1)
abstract class CacheDB : RoomDatabase(){
    abstract fun weatherDataDao() : WeatherDataDao
}

@Entity(tableName = "LocalSky")
data class UserWeatherData(
    @PrimaryKey()
    val userID: String,
    @ColumnInfo
    val lastWeatherData: WeatherData
)

@Dao
interface WeatherDataDao {
    @Query("SELECT * FROM LocalSky WHERE userID LIKE :uid LIMIT 1")
    fun findByUserID(uid: String): UserWeatherData

    @Insert
    fun insert(userWeatherData: UserWeatherData)

    @Delete
    fun delete(userWeatherData: UserWeatherData)
}