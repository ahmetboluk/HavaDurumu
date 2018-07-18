package com.ahmetboluk.havadurumu.network;


import com.ahmetboluk.havadurumu.model.Forecast;
import com.ahmetboluk.havadurumu.model.SingleWeather;
import com.ahmetboluk.havadurumu.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {
    @GET("forecast")
    Call<Forecast> getForecastsByLatLng(@Query("lat") double lat, @Query("lon") double lon, @Query("lang") String lang,@Query("units") String units, @Query("appid") String apikey);
    @GET("weather")
    Call<SingleWeather> getWeatherByLatLng(@Query("lat") double lat, @Query("lon") double lon, @Query("lang") String lang, @Query("units") String units, @Query("appid") String apikey);
}
