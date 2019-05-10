package com.example.myappweather.persistance;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.myappweather.models.CurrentArea;
import com.example.myappweather.models.CurrentWeather;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
@Dao
public interface WeatherDao {


    @Insert(onConflict = IGNORE)
     long insertWeather(CurrentArea weather);

    @Insert(onConflict = REPLACE)
    void insertToWeather(CurrentArea weather);

    // Custom update statement so ingredients and timestamp don't get removed
    @Query("UPDATE weather_table SET currentWeather = :weather, timezone = :area WHERE id = :id")
    void updateRecipe(int id, CurrentWeather weather, String area);

    // NOTE: The SQL query sometimes won't return EXACTLY what the api does since the API might use a different query
    // or even a different database. But they are very very close.

    @Query("SELECT * FROM weather_table")
    LiveData<CurrentArea> getDayWeather();
}
