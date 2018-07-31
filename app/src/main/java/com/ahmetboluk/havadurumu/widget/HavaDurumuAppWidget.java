package com.ahmetboluk.havadurumu.widget;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ahmetboluk.havadurumu.Constant;
import com.ahmetboluk.havadurumu.R;
import com.ahmetboluk.havadurumu.model.Forecast;
import com.ahmetboluk.havadurumu.model.SingleWeather;
import com.ahmetboluk.havadurumu.network.NetworkClient;
import com.ahmetboluk.havadurumu.network.NetworkInterface;
import com.ahmetboluk.havadurumu.service.MyService;
import com.ahmetboluk.havadurumu.ui.main.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 */
public class HavaDurumuAppWidget extends AppWidgetProvider implements HavaDurumuAppWidgetViewInterface{


    private LocationCallback mLocationCallback;

    private LocationRequest mLocationRequest;
    public HavaDurumuAppWidgetPresenter havaDurumuAppWidgetPresenter;


    private FusedLocationProviderClient mFusedLocationClient;
    AppWidgetManager appWidgetManager;
    RemoteViews views;
    int appWidgetId;


    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        views = new RemoteViews(context.getPackageName(), R.layout.hava_durumu_app_widget);
        Intent intent = new Intent(context,MyService.class);
        context.startService(intent);
        this.appWidgetManager = appWidgetManager;
        this.appWidgetId=appWidgetId;
        havaDurumuAppWidgetPresenter = new HavaDurumuAppWidgetPresenter(context,this);

        havaDurumuAppWidgetPresenter.getLastLocation();
        havaDurumuAppWidgetPresenter.startLocationUpdates();

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    @Override
    public void displayLastLocation(SingleWeather singleWeather) {
        views.setTextViewText(R.id.city_name,singleWeather.getName());
        views.setTextViewText(R.id.weather_degree,singleWeather.getMain().getTemp().intValue() + "°C");
        views.setTextViewText(R.id.weather_description,singleWeather.getWeather().get(0).getDescription());

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void displayLocationUpdate(SingleWeather singleWeather) {
        views.setTextViewText(R.id.city_name,singleWeather.getName());
        views.setTextViewText(R.id.weather_degree,singleWeather.getMain().getTemp().intValue() + "°C");
        views.setTextViewText(R.id.weather_description,singleWeather.getWeather().get(0).getDescription());

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    /*
    *
    *
    *
    *
    *
    *
     */

}

