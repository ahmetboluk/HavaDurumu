package com.ahmetboluk.havadurumu.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmetboluk.havadurumu.R;
import com.ahmetboluk.havadurumu.model.Forecast;


public class ThreeHourForecastAdapter extends RecyclerView.Adapter<ThreeHourForecastAdapter.MyViewHolder>{

    Forecast forecast;
    Context context;

    public ThreeHourForecastAdapter(Forecast forecast, Context context){
        this.forecast=forecast;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_weather_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return forecast.getList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
