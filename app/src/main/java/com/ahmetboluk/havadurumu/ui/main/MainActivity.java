package com.ahmetboluk.havadurumu.ui.main;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.ahmetboluk.havadurumu.R;
import com.ahmetboluk.havadurumu.model.Forecast;
import com.ahmetboluk.havadurumu.ui.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainViewInterface {
    MainPresenter mainPresenter;
    RecyclerView rvForecast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 0);
        }
        mainPresenter = new MainPresenter(this);
        mainPresenter.getForecasts();
        rvForecast=findViewById(R.id.five_days_forecast);
        rvForecast.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void displayDailyForecasts(Forecast forecast) {
        Log.d("Response",forecast.getList().get(0).getMain().getTemp().toString());
        ThreeHourForecastAdapter adapter=new ThreeHourForecastAdapter(forecast,getApplicationContext());
        rvForecast.setAdapter(adapter);
    }
}
