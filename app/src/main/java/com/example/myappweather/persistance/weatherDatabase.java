package com.example.myappweather.persistance;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.myappweather.models.CurrentArea;
import com.example.myappweather.models.DataConverter;

@Database(entities = {CurrentArea.class}, version = 1,exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class weatherDatabase extends RoomDatabase {


    private static final String db_name = "WeatherDB";
    private static weatherDatabase instance;

    public abstract WeatherDao getWeatherDao();

    public static weatherDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), weatherDatabase.class, db_name)
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }

}

