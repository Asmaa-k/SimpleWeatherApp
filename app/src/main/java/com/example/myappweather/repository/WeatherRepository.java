package com.example.myappweather.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.myappweather.models.CurrentArea;
import com.example.myappweather.persistance.WeatherDao;
import com.example.myappweather.persistance.weatherDatabase;
import com.example.myappweather.request.AppExecutor;
import com.example.myappweather.request.ServiceGenerator;
import com.example.myappweather.request.response.ApiResponse;
import com.example.myappweather.request.response.NetworkBoundResource;
import com.example.myappweather.request.response.Resource;
import com.example.myappweather.util.Constants;

public class WeatherRepository {

    private static WeatherRepository instance;
    private WeatherDao weatherDao;
    private static final String TAG = "WeatherRepository";

    public static WeatherRepository getInstance(Context context) {
        if (instance == null) {
            instance = new WeatherRepository(context);
        }
        return instance;
    }

    private WeatherRepository(Context context) {
        weatherDao = weatherDatabase.getInstance(context).getWeatherDao();
    }


    public LiveData<Resource<CurrentArea>> getToDayWeather() {
        return new NetworkBoundResource<CurrentArea, CurrentArea>(AppExecutor.getInstant()) {

            @Override
            public void saveCallResult(@NonNull CurrentArea item) {
                //decide weather or not to save data into cache every time user make new request

                CurrentArea currentArea = new CurrentArea(item.getTimezone(), item.getCurrentWeather());

                long rowId = weatherDao.insertWeather(currentArea);
                if (rowId == -1) { // conflict detected
                    Log.d(TAG, "saveCallResult: CONFLICT... This recipe is already in cache.");
                    // if already exists, I don't want to set the ingredients or timestamp b/c they will be erased
                    weatherDao.updateRecipe(
                            currentArea.getId(), currentArea.getCurrentWeather(), currentArea.getTimezone()
                    );
                }
            }

            @Override
            public boolean shouldFetch(@Nullable CurrentArea data) {//Based on time stampe (when should i refresh the data)

                if (data == null) return true;
                int currentTime = (int) (System.currentTimeMillis() / 1000);
                Log.d(TAG, "shouldFetch: current time: " + currentTime);

                int lastRefresh =(int) data.getCurrentWeather().getTime();
                Log.d(TAG, "shouldFetch: last refresh: " + lastRefresh);

                Log.d(TAG, "shouldFetch: it's been " + ((currentTime - lastRefresh) / 60 * 60 / 24) +
                        "days since this recipe was refreshed.. 30 days must elapse before refreshing");

                if ((currentTime - data.getCurrentWeather().getTime()) >= Constants.Weather_REFRESH_TIME) {
                    Log.d(TAG, "shouldFetch: Should Refresh Recipe " + true);
                    return true;
                }
                Log.d(TAG, "shouldFetch: Should Refresh Recipe " + false);
                return false;
            }

            @NonNull
            @Override
            public LiveData<CurrentArea> loadFromDb() {
                return weatherDao.getDayWeather();
            }

            @NonNull
            @Override
            public LiveData<ApiResponse<CurrentArea>> createCall() {//i should convert call to live data
                return ServiceGenerator.getWeatherApi().getTodayWeather();
            }
        }.getAsLiveData();
    }
}