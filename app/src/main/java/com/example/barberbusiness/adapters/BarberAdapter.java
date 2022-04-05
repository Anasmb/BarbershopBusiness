package com.example.barberbusiness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberbusiness.R;
import com.example.barberbusiness.items.BarberItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BarberAdapter extends RecyclerView.Adapter<BarberAdapter.BarberViewHolder> {

    private Context mContext;
    private List<BarberItem> barberItemList;

    public BarberAdapter(Context mContext, List<BarberItem> barberItemList) {
        this.mContext = mContext;
        this.barberItemList = barberItemList;
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
        BarberItem barberItem = barberItemList.get(position);
        holder.barberName.setText(barberItem.getBarberName());
    }

    public int getItemCount() {
        return barberItemList.size();
    }


    class BarberViewHolder extends RecyclerView.ViewHolder {

        TextView barberName;

        public BarberViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            barberName = itemView.findViewById(R.id.barberNameTextView);
        }

    }
}
