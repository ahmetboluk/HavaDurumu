package com.ahmetboluk.havadurumu.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmetboluk.havadurumu.R;
import com.ahmetboluk.havadurumu.model.Forecast;
import com.bumptech.glide.Glide;



public class ThreeHourForecastAdapter extends RecyclerView.Adapter<ThreeHourForecastAdapter.DailyWeatherViewHolder>{

    Forecast forecast;
    Context context;

    public ThreeHourForecastAdapter(Forecast forecast, Context context){
        this.forecast=forecast;
        this.context=context;
    }

    @NonNull
    @Override
    public DailyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_weather_item,parent,false);
        return new DailyWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherViewHolder holder, int position) {
       holder.time.setText(position==0?"NOW":forecast.getList().get(position).getDtTxt().substring(10,16));
       Glide.with(context).load("http://openweathermap.org/img/w/"+forecast.getList().get(position).getWeather().get(0).getIcon()+".png").into(holder.icon);
       holder.temperature.setText(forecast.getList().get(position).getMain().getTemp().intValue()+" °C");
    }

    @Override
    public int getItemCount() {
        return forecast.getList().size();
    }

    public class DailyWeatherViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        ImageView icon;
        TextView temperature;
        public DailyWeatherViewHolder(View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.time);
            icon=itemView.findViewById(R.id.weather_icon);
            temperature=itemView.findViewById(R.id.temperature);
        }
    }
}
