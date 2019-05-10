package com.example.myappweather.models;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DataConverter {

    @TypeConverter
    public String fromWeatherObj(CurrentWeather currentWeather) {
        if (currentWeather == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<CurrentWeather>() {
        }.getType();
        String json = gson.toJson(currentWeather, type);
        return json;
    }

    @TypeConverter
    public CurrentWeather toWeatherObj(String currentWeather) {
        if (currentWeather == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<CurrentWeather>() {
        }.getType();
        CurrentWeather currentWeatherData = gson.fromJson(currentWeather, type);
        return currentWeatherData;
    }
}
