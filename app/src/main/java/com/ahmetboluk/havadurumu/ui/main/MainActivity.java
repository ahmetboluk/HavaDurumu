package com.ahmetboluk.havadurumu.ui.main;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.ahmetboluk.havadurumu.Constant;
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
    protected void onPause() {
        super.onPause();
        mainPresenter.stopLocationUpdates();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.startLocationUpdates();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(MainActivity.this,this);

        rvForecast = findViewById(R.id.five_days_forecast);
        rvForecast.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        location=findViewById(R.id.location);
        condition=findViewById(R.id.condition);
        degree=findViewById(R.id.degree);

        getLocationProcess();

    }

    public void getLocationProcess(){ mainPresenter.locationProcess(); }

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
        degree.setText(singleWeather.getMain().getTemp().intValue() + "°C");
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        Log.d("buradamısın", "burada");
        switch (requestCode) {
            case Constant.REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainPresenter.locationProcess();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}
