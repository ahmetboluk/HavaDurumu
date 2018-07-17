package com.ahmetboluk.havadurumu.network;


import com.ahmetboluk.havadurumu.model.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {
    @GET("forecast")
    Call<Forecast> getForecastsByLatLng(@Query("lat") String lat, @Query("lon") String lon, @Query("lang") String lang,@Query("units") String units, @Query("appid") String apikey);
}
