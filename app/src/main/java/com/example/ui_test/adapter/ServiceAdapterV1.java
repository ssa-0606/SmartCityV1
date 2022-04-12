package com.example.ui_test.adapter;

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

public class ServiceAdapterV1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SmartCityService> services;
    private int resourceId;
    private ServiceAdapter.ViewHolder holder;

    public ServiceAdapterV1(List<SmartCityService> services, int resourceId) {
        this.services = services;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, null);
        holder = new ServiceAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SmartCityService service = services.get(position);
        TextView textView = holder.itemView.findViewById(R.id.name_service);
        ImageView imageView = holder.itemView.findViewById(R.id.img_service);
        textView.setText(service.getServiceName());
        Glide.with(holder.itemView).load(service.getImgUrl()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
}
