package com.ahmetboluk.havadurumu.network;


import com.ahmetboluk.havadurumu.model.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {
    @GET("forecast")
    Call<Model> getForecastsByLatLng(@Query("lat") String lat, @Query("lon") String lon,@Query("lang") String lang, @Query("appid") String apikey);
}
