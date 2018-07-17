package com.ahmetboluk.havadurumu.ui.main;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ahmetboluk.havadurumu.R;
import com.ahmetboluk.havadurumu.model.Forecast;
import com.ahmetboluk.havadurumu.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainViewInterface {
    MainPresenter mainPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 0);
        }
        mainPresenter = new MainPresenter(this);
        mainPresenter.getForecasts();
    }

    @Override
    public void displayDailyForecasts(Forecast forecast) {
        Log.d("Response",forecast.getCity().getName());
    }
}
