package com.example.myappweather;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappweather.livedata.WeatherLiveData;
import com.example.myappweather.models.CurrentArea;
import com.example.myappweather.request.response.Resource;
import com.example.myappweather.util.Constants;

public class weatherScreen extends AppCompatActivity {

    WeatherLiveData mLiveData;
    private static final String TAG = "MainActivity";

    TextView Zone,Temper,Time,Wind,Pressure;
    ImageView Icone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_screen);

        Zone = findViewById(R.id.name_loc);
        Temper = findViewById(R.id.avg_temp);
        Time = findViewById(R.id.time);
        Wind = findViewById(R.id.wind_rate);
        Pressure = findViewById(R.id.pressu_rate);
        Icone = findViewById(R.id.image_wheater);

        mLiveData = ViewModelProviders.of(this).get(WeatherLiveData.class);
        searchRecipeApi();
        subscribeObservers();
    }

    private void subscribeObservers() {
        mLiveData.getWeather().observe(this, new Observer<Resource<CurrentArea>>() {
            @Override
            public void onChanged(@Nullable Resource<CurrentArea> listResource) {
                if (listResource != null) {
                    Log.d(TAG, "onChanged: status: " + listResource.status);

                    if (listResource.data != null) {
                        switch (listResource.status) {
                            case LOADING: {
                                break;
                            }
                            case SUCCESS: {
                                Log.d(TAG, "onChanged: cache has been refreshed.");
                                Log.d(TAG, "onChanged: status: SUCCESS,: ");
                                Zone.setText(listResource.data.getTimezone());
                                Time.setText( Constants.timeConvert(listResource.data.getCurrentWeather().getTime()));
                                Temper.setText( Constants.celsiusConvert(listResource.data.getCurrentWeather().getTemperature())+"");
                                Pressure.setText( String.valueOf(listResource.data.getCurrentWeather().getPressure()));
                                Wind.setText(Math.round(listResource.data.getCurrentWeather().getWindSpeed())+"m/s");
                                Icone.setImageDrawable(ResourcesCompat.getDrawable(getResources()
                                        , Constants.setDrawableWeather(listResource.data.getCurrentWeather().getIcon())
                                        ,null));
                                break;
                            }
                            case ERROR: {
                                Log.e(TAG, "onChanged: cannot refresh cache.");
                                Log.e(TAG, "onChanged: ERROR message: " + listResource.message);
                                Log.e(TAG, "onChanged: status: ERROR, ");
                                break;
                            }
                        }
                    }

                }
            }
        });
    }



    private void searchRecipeApi() {
        mLiveData.getToDayWeather();
    }

}
