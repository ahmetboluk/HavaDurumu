package com.ahmetboluk.havadurumu.ui.main;

import android.util.Log;

import com.ahmetboluk.havadurumu.Constant;
import com.ahmetboluk.havadurumu.model.Forecast;
import com.ahmetboluk.havadurumu.network.NetworkClient;
import com.ahmetboluk.havadurumu.network.NetworkInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainPresenterInterface {
    MainViewInterface mainViewInterface;
    public MainPresenter(MainViewInterface mainViewInterface) {
        this.mainViewInterface=mainViewInterface;
    }

    @Override
    public void getForecasts() {
        NetworkClient.getInstance().create(NetworkInterface.class).getForecastsByLatLng("40.9","29.1","tr", Constant.API_KEY).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Log.d("Response",response.raw()+"");
                mainViewInterface.displayDailyForecasts(response.body());
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
