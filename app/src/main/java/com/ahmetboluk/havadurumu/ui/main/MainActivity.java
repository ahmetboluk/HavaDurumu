package com.ahmetboluk.havadurumu.ui.main;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ahmetboluk.havadurumu.Constant;
import com.ahmetboluk.havadurumu.model.Model;
import com.ahmetboluk.havadurumu.network.NetworkClient;
import com.ahmetboluk.havadurumu.network.NetworkInterface;
import com.ahmetboluk.havadurumu.ui.base.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 0);
        }
        NetworkClient.getInstance().create(NetworkInterface.class).getForecastsByLatLng("40.9","29.1","tr", Constant.API_KEY).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.d("Response",response.body().getList().get(0).getWeather().get(0).getDescription());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
