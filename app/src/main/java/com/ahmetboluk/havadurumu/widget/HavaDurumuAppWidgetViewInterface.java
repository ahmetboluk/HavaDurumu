package com.ahmetboluk.havadurumu.widget;

import com.ahmetboluk.havadurumu.model.SingleWeather;

public interface HavaDurumuAppWidgetViewInterface {
    void displayLastLocation(SingleWeather singleWeather);
    void displayLocationUpdate(SingleWeather singleWeather);
}
