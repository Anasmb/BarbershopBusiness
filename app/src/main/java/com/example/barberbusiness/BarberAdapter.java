package com.example.barberbusiness;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BarberAdapter extends RecyclerView.Adapter<BarberAdapter.BarberViewHolder> {

    private Context mContext;
    private List<String> BarberItemList;

    public BarberAdapter(Context mContext, List<String> barberItemList) {
        this.mContext = mContext;
        this.BarberItemList = barberItemList;
    }

    @NonNull
    @NotNull
    @Override
    public BarberViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.barber_item, null);
        BarberViewHolder holder = new BarberViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BarberAdapter.BarberViewHolder holder, int position) {
        String barberItem = BarberItemList.get(position);
        holder.barberName.setText(barberItem);
    }

    public int getItemCount() {
        return BarberItemList.size();
    }


    class BarberViewHolder extends RecyclerView.ViewHolder {

        TextView barberName;

        public BarberViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            barberName = itemView.findViewById(R.id.barberNameTextView);
        }

    }
}
