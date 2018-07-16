package com.ahmetboluk.havadurumu.network;

import com.ahmetboluk.havadurumu.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    static Retrofit retrofit;
   public static Retrofit getInstance(){
       if (retrofit==null){
           retrofit=new Retrofit.Builder()
                   .baseUrl(Constant.BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
       }
       return retrofit;
   }
}
