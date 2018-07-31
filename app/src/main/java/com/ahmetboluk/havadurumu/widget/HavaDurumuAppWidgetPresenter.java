package com.ahmetboluk.havadurumu.widget;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.ahmetboluk.havadurumu.Constant;
import com.ahmetboluk.havadurumu.R;
import com.ahmetboluk.havadurumu.model.SingleWeather;
import com.ahmetboluk.havadurumu.network.NetworkClient;
import com.ahmetboluk.havadurumu.network.NetworkInterface;
import com.ahmetboluk.havadurumu.ui.main.MainViewInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HavaDurumuAppWidgetPresenter implements HavaDurumuAppWidgetInterface {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    Context context;
    HavaDurumuAppWidgetViewInterface havaDurumuAppWidgetViewInterface;


    public HavaDurumuAppWidgetPresenter(Context context , HavaDurumuAppWidgetViewInterface havaDurumuAppWidgetViewInterface){

        this.context=context;
        this.havaDurumuAppWidgetViewInterface=havaDurumuAppWidgetViewInterface;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    @Override
    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        NetworkClient.getInstance().create(NetworkInterface.class).getWeatherByLatLng(location.getLatitude(), location.getLongitude(), "tr", "metric", Constant.API_KEY).enqueue(new Callback<SingleWeather>() {
                            @Override
                            public void onResponse(Call<SingleWeather> call, Response<SingleWeather> response) {
                                havaDurumuAppWidgetViewInterface.displayLastLocation(response.body());
                            }

                            @Override
                            public void onFailure(Call<SingleWeather> call, Throwable t) {


                            }
                        });

                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });

    }

    @Override
    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {

                    Log.d("UpdatedLocation",location.getLatitude()+" - "+location.getLongitude());
                    NetworkClient.getInstance().create(NetworkInterface.class).getWeatherByLatLng(location.getLatitude(), location.getLongitude(), "tr", "metric", Constant.API_KEY).enqueue(new Callback<SingleWeather>() {
                        @Override
                        public void onResponse(Call<SingleWeather> call, Response<SingleWeather> response) {
                            havaDurumuAppWidgetViewInterface.displayLocationUpdate(response.body());
                        }

                        @Override
                        public void onFailure(Call<SingleWeather> call, Throwable t) {

                        }
                    });

                }
            }
        };
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }
}
