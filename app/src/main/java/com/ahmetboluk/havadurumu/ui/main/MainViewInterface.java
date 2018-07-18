package com.ahmetboluk.havadurumu.ui.main;

import com.ahmetboluk.havadurumu.model.Forecast;
import com.ahmetboluk.havadurumu.model.SingleWeather;

public interface MainViewInterface {
    void displayDailyForecasts(Forecast forecast);
    void displaySingleWeather(SingleWeather singleWeather);
}
