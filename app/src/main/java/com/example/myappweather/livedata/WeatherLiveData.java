package com.example.myappweather.livedata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.myappweather.models.CurrentArea;
import com.example.myappweather.repository.WeatherRepository;
import com.example.myappweather.request.response.Resource;

public class WeatherLiveData extends AndroidViewModel {
    private static final String TAG = "RecipeListViewModel";



    private MediatorLiveData<Resource<CurrentArea>> weather = new MediatorLiveData<>();
    private WeatherRepository weatherRepository;

    // query extra
    private long requestStartTime;


    public WeatherLiveData(@NonNull Application application) {
        super(application);
        weatherRepository = WeatherRepository.getInstance(application);
    }


    public LiveData<Resource<CurrentArea>> getWeather() {
        return weather;
    }

    public void getToDayWeather() {
        requestStartTime = System.currentTimeMillis();

        final LiveData<Resource<CurrentArea>> repositorySource = weatherRepository.getToDayWeather();

        weather.addSource(repositorySource, new Observer<Resource<CurrentArea>>() {
            @Override
            public void onChanged(@Nullable Resource<CurrentArea> listResource) { //react to the data
                    if (listResource != null) {
                        weather.setValue(listResource);
                        if (listResource.status == Resource.Status.SUCCESS) {
                            Log.d(TAG, "onChanged: Request Time" + (System.currentTimeMillis() - requestStartTime) / 1000 + "seconds");
                            if (listResource.data == null) {
                                weather.setValue(new Resource<CurrentArea>(
                                        Resource.Status.ERROR,
                                        listResource.data,
                                        listResource.message));
                            }
                            weather.removeSource(repositorySource);
                        } else if (listResource.status == Resource.Status.ERROR) {
                            Log.d(TAG, "onChanged: Request Time" + (System.currentTimeMillis() - requestStartTime) / 1000 + "seconds");
                            weather.removeSource(repositorySource);
                        }
                    } else {
                        weather.removeSource(repositorySource);
                        //we no longer need to observe it so we should remove it(the observer will continue observe it if we didn't remove it)
                    }

            }
        });
    }
}

