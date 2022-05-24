package com.example.barberbusiness.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberbusiness.R;
import com.example.barberbusiness.items.AppointmentItem;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private Context mContext;
    private List<AppointmentItem> appointmentItemList;
    private OnItemListener mOnItemListener;

    public AppointmentAdapter(Context mContext, List<AppointmentItem> appointmentItems , OnItemListener onItemListener) {
        this.mContext = mContext;
        this.appointmentItemList = appointmentItems;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @NotNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.appointment_item, null);
        AppointmentAdapter.AppointmentViewHolder holder = new AppointmentAdapter.AppointmentViewHolder(view,mOnItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AppointmentAdapter.AppointmentViewHolder holder, int position) {
        AppointmentItem appointmentItem = appointmentItemList.get(position);

        holder.customer.setText(appointmentItem.getCustomer());
        holder.barber.setText(appointmentItem.getBarber());
        holder.dateTime.setText(appointmentItem.getDateTime());
        holder.serviceAt.setText(appointmentItem.getServiceAt());
        holder.services.setText(appointmentItem.getServices());
        holder.totalPrice.setText(appointmentItem.getPrice());

        if (appointmentItem.getStatus().equals("Declined")){
            holder.declineText.setClickable(false);
            holder.declineText.setTextColor(Color.parseColor("#AAAAAA"));
            holder.confirmText.setVisibility(View.GONE);
        }
        else if (appointmentItem.getStatus().equals("Confirmed")){
            holder.confirmText.setVisibility(View.GONE);
            holder.declineText.setVisibility(View.GONE);
            holder.finishText.setVisibility(View.VISIBLE);
        }
        else if (appointmentItem.getStatus().equals("Finished")){
            holder.finishText.setVisibility(View.VISIBLE);
            holder.finishText.setClickable(false);
            holder.finishText.setTextColor(Color.parseColor("#AAAAAA"));
            holder.confirmText.setVisibility(View.GONE);
            holder.declineText.setVisibility(View.GONE);
        }

        holder.confirmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.confirmText.setVisibility(View.GONE);
                holder.declineText.setVisibility(View.GONE);
                holder.finishText.setVisibility(View.VISIBLE);
                changeStatus(appointmentItem.getAppointmentID() , "Confirmed");

            }
        });

        holder.declineText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.declineText.setTextColor(Color.parseColor("#AAAAAA"));
                holder.confirmText.setVisibility(View.GONE);
                changeStatus(appointmentItem.getAppointmentID() , "Declined");
            }
        });

        holder.finishText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.finishText.setTextColor(Color.parseColor("#AAAAAA"));
                changeStatus(appointmentItem.getAppointmentID() , "Finished");
            }
        });



    }

    @Override
    public int getItemCount() {
        return appointmentItemList.size();
    }

    private void changeStatus(String appointmentID , String status){
        Log.d("debug", "changeStatus: " + appointmentID + " , " + status);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[2];
                field[0] = "appointmentID";
                field[1] = "status";
                String[] data = new String[2];
                data[0] = appointmentID;
                data[1] = status;
                PutData putData = new PutData("http://192.168.100.6/barbershop-php/changeStatus.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.d("php", result);
                    }
                }

            }
        });
    }



    class AppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView customer, barber ,dateTime, serviceAt, services, totalPrice , confirmText, declineText , finishText;
        OnItemListener onItemListener;

        public AppointmentViewHolder(@NonNull @NotNull View itemView, OnItemListener onItemListener) {
            super(itemView);

            customer = itemView.findViewById(R.id.appointmentCustomerName);
            barber = itemView.findViewById(R.id.appointmentBarber);
            dateTime = itemView.findViewById(R.id.appointmentDateTime);
            serviceAt = itemView.findViewById(R.id.appointmentServiceLocation);
            services = itemView.findViewById(R.id.appointmentServicesText);
            totalPrice = itemView.findViewById(R.id.appointmentPriceTxt);

            confirmText = itemView.findViewById(R.id.appointmentConfirmText);
            declineText = itemView.findViewById(R.id.appointmentDeclineText);
            finishText = itemView.findViewById(R.id.appointmentFinishText);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(view,getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(View view, int position);
    }

}
