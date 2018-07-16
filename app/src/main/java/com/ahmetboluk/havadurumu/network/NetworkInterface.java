package com.ahmetboluk.havadurumu.network;


import com.ahmetboluk.havadurumu.model.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {
    @GET("forecast/daily")
    Call<Forecast> getForecastsByLatLng(@Query("lat") String lat, @Query("lon") String lon,@Query("cnt") String count, @Query("lang") String lang, @Query("appid") String apikey);
}
