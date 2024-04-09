package com.ls.localsky

import android.content.Context
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.ls.localsky.models.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class CacheLS(
    context: Context
) {
    private val cache = Room.databaseBuilder(
        context,
        CacheDB::class.java, "LocalSkyDatabase"
    ).build()

    private val gson = Gson()

    fun getCachedWeatherData(): WeatherData? {
        Log.d(TAG, "Getting Cached Weather Data")
        val weatherData = cache.weatherDataDao().getWeatherData(1)
        return if(weatherData == null){
            null
        } else{
            gson.fromJson(weatherData.lastWeatherData, WeatherData::class.java)
        }
    }

    fun updateCachedWeatherData(weatherData: WeatherData){
        Log.d(TAG, "Updating Cached Weather Data")
        CoroutineScope(Dispatchers.IO).launch {
            cache.weatherDataDao().insert(UserWeatherData(1, gson.toJson(weatherData)))
        }
    }

    companion object{
        const val TAG = "Cache"
    }

}

@Database(entities = [UserWeatherData::class], version = 1)
abstract class CacheDB : RoomDatabase(){
    abstract fun weatherDataDao() : WeatherDataDao
}

@Entity(tableName = "LocalSky")
data class UserWeatherData(
    @PrimaryKey()
    val index: Int,
    @ColumnInfo
    val lastWeatherData: String
)

@Dao
interface WeatherDataDao {
    @Query("SELECT * FROM LocalSky WHERE `index` LIKE :ind LIMIT 1")
    fun getWeatherData(ind: Int): UserWeatherData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userWeatherData: UserWeatherData)

    @Delete
    fun delete(userWeatherData: UserWeatherData)
}