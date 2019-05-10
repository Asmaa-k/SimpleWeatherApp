package com.example.myappweather.models;

import android.arch.persistence.room.Ignore;

public class CurrentWeather {

    long time;
    float temperature;
    float pressure;
    float windSpeed;
    String icon;

    @Ignore
    CurrentWeather() {
    }

    public CurrentWeather(long time, float temperature, float pressure, float windSpeed, String icon) {
        this.time = time;
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.icon = icon;

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

