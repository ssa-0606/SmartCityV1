package com.example.ui_test.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ui_test.R;
import com.example.ui_test.pojo.SmartCityService;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_service);
            textView = (TextView) itemView.findViewById(R.id.name_service);
        }
    }

    private List<SmartCityService> services;
    private int resourceId;

    public ServiceAdapter(List<SmartCityService> services, int resourceId) {
        this.services = services;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SmartCityService smartCityService = services.get(position);
//        Log.d("TAG1", "onBindViewHolder: "+smartCityService.getServiceName());
        holder.textView.setText(smartCityService.getServiceName());
        Glide.with(holder.itemView).load(smartCityService.getImgUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
}
