package com.example.barberbusiness;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private Context mContext;
    private List<ServiceItem> serviceItemList;

    public ServiceAdapter(Context mContext, List<ServiceItem> serviceItemList) {
        this.mContext = mContext;
        this.serviceItemList = serviceItemList;
    }

    @NonNull
    @NotNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.service_item, null);
        ServiceViewHolder holder = new ServiceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ServiceAdapter.ServiceViewHolder holder, int position) {
        ServiceItem serviceItem = serviceItemList.get(position);

        holder.serviceName.setText(serviceItem.getServiceName());
        holder.duration.setText(serviceItem.getDuration());
        holder.price.setText(Double.toString(serviceItem.getPrice()));
    }

    public int getItemCount() {
        return serviceItemList.size();
    }


    class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName;
        TextView duration;
        TextView price;

        public ServiceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceNameTextView);
            duration = itemView.findViewById(R.id.serviceDurationTextView);
            price = itemView.findViewById(R.id.servicePriceTextView);
        }

    }
}
