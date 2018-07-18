package com.ahmetboluk.havadurumu.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.ahmetboluk.havadurumu.Constant;
import com.ahmetboluk.havadurumu.model.Forecast;
import com.ahmetboluk.havadurumu.model.SingleWeather;
import com.ahmetboluk.havadurumu.model.Weather;
import com.ahmetboluk.havadurumu.network.NetworkClient;
import com.ahmetboluk.havadurumu.network.NetworkInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainPresenterInterface, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {
    GoogleApiClient mGoogleApiClient;
    Task<Location> mLastLocation;
    MainViewInterface mainViewInterface;
    Activity activity;
    double[] location;


    public MainPresenter(Activity activity, MainViewInterface mainViewInterface) {
        this.mainViewInterface = mainViewInterface;
        this.activity = activity;
    }

    @Override
    public void getForecasts(double latitude,double longitude) {

    }

    void locationProcess() {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build());

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        mLastLocation = LocationServices.getFusedLocationProviderClient(activity).getLastLocation();
        mLastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()){
                    NetworkClient.getInstance().create(NetworkInterface.class).getWeatherByLatLng( task.getResult().getLatitude(),task.getResult().getLongitude(), "tr", "metric", Constant.API_KEY).enqueue(new Callback<SingleWeather>() {
                        @Override
                        public void onResponse(Call<SingleWeather> call, Response<SingleWeather> response) {
                            mainViewInterface.displaySingleWeather(response.body());
                        }

                        @Override
                        public void onFailure(Call<SingleWeather> call, Throwable t) {

                        }
                    });
                    NetworkClient.getInstance().create(NetworkInterface.class).getForecastsByLatLng( task.getResult().getLatitude(),task.getResult().getLongitude(), "tr", "metric", Constant.API_KEY).enqueue(new Callback<Forecast>() {
                        @Override
                        public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                            Log.d("Response", response.raw() + "");
                            mainViewInterface.displayDailyForecasts(response.body());
                        }

                        @Override
                        public void onFailure(Call<Forecast> call, Throwable t) {
                            Log.d("Error", t.getMessage());
                        }
                    });
                }
            }
        });

    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Location", "Connection failed: ConnectionResult.getErrorCode() = "+ result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
// Once connected with google api, get the location

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }
    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(activity);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(activity,resultCode,
                        0).show();
            } else {
                Toast.makeText(activity,
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }
    public Address getAddress(double latitude, double longitude)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(activity, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }
}
