package com.example.myappweather.request;

import android.arch.lifecycle.LiveData;

import com.example.myappweather.models.CurrentArea;
import com.example.myappweather.request.response.ApiResponse;
import com.example.myappweather.util.Constants;
import retrofit2.http.GET;

public interface WeatherApi {
    String req ="forecast/"+ Constants.API_KEY+"/"+Constants.LATLNG;

    @GET(req)
    LiveData<ApiResponse<CurrentArea>> getTodayWeather();

}
