package com.ahmetboluk.havadurumu.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.ahmetboluk.havadurumu.R;
import com.ahmetboluk.havadurumu.model.Forecast;
import com.ahmetboluk.havadurumu.model.SingleWeather;
import com.ahmetboluk.havadurumu.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainViewInterface{
    MainPresenter mainPresenter;
    RecyclerView rvForecast;
    TextView location;
    TextView condition;
    TextView degree;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(MainActivity.this,this);
        mainPresenter.locationProcess();
        if (mainPresenter.location!=null){
            Log.d("Latitude",mainPresenter.location[0]+"");
            Log.d("Longitude",mainPresenter.location[1]+"");
            mainPresenter.getForecasts(mainPresenter.location[0],mainPresenter.location[1]);
        }
        rvForecast = findViewById(R.id.five_days_forecast);
        rvForecast.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        location=findViewById(R.id.location);
        condition=findViewById(R.id.condition);
        degree=findViewById(R.id.degree);
    }

    @Override
    public void displayDailyForecasts(Forecast forecast) {
        Log.d("Response", forecast.getList().get(0).getMain().getTemp().toString());
        ThreeHourForecastAdapter adapter = new ThreeHourForecastAdapter(forecast, getApplicationContext());
        rvForecast.setAdapter(adapter);
    }

    @Override
    public void displaySingleWeather(SingleWeather singleWeather) {
        location.setText(singleWeather.getName());
        condition.setText(singleWeather.getWeather().get(0).getDescription());
        degree.setText(singleWeather.getMain().getTemp().intValue() + " °C");
    }

}
