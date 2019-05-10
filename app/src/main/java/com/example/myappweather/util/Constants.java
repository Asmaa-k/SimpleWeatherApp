package com.example.myappweather.util;

import com.example.myappweather.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
    public static final String BASE_URL = "https://api.darksky.net";
    public static final String API_KEY = "a7a0fe25d3a344d8a7fe54c4a4e8bcb1";
    public static final int CONNECTION_TIMEOUT = 10;//10 SECOND
    public static final int READ_TIMEOUT = 2;//2 SECOND
    public static final int WRITE_TIMEOUT = 2;//2 SECOND
    public static final String LATLNG = "31.963158, 35.930359";

    public static final int Weather_REFRESH_TIME = 60 * 60 * 24;// 30 days


    public static int setDrawableWeather(String desc) {
        switch (desc) {
            case "clear-day":
                return R.drawable.suny;
            case "clear-night":
                return R.drawable.suny;
            case "partly-cloudy-day":
                return R.drawable.half_cloud;
            case "partly-cloudy-night":
                return R.drawable.half_cloud;
            case "rain":
                return R.drawable.rainy;
            case "cloudy":
                return R.drawable.cloudy;
            case "wind":
                return R.drawable.windy;
            default:
                return R.drawable.suny;
        }
    }

    public static int celsiusConvert(float feh) {
        float cel = ((feh - 32) * 5) / 9;
        return Math.round(cel);
    }

    public static String timeConvert(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }

}
