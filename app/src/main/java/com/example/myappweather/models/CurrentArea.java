package com.example.myappweather.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "weather_table")
public class CurrentArea {

    @PrimaryKey(autoGenerate = true)
    int id;

    String timezone;

    @SerializedName("currently")
    @Expose
    CurrentWeather currentWeather;

    @Ignore
    public CurrentArea() {
    }

    public CurrentArea(String timezone, CurrentWeather currentWeather) {
        this.timezone = timezone;
        this.currentWeather = currentWeather;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimezone() {
        return timezone;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
