package com.example.barberbusiness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberbusiness.R;
import com.example.barberbusiness.items.FeedbackItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private Context mContext;
    private List<FeedbackItem> feedbackItemList;

    public FeedbackAdapter(Context mContext, List<FeedbackItem> feedbackItemList) {
        this.mContext = mContext;
        this.feedbackItemList = feedbackItemList;
    }

    @NonNull
    @NotNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.feedback_item, null);
        FeedbackViewHolder holder = new FeedbackViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeedbackAdapter.FeedbackViewHolder holder, int position) {
        FeedbackItem feedbackItem = feedbackItemList.get(position);

        holder.stars.setText(String.valueOf(feedbackItem.getStars()));
        holder.comment.setText(feedbackItem.getComment());
    }

    @Override
    public int getItemCount() {
        return feedbackItemList.size();
    }


    class FeedbackViewHolder extends RecyclerView.ViewHolder {

        TextView stars;
        TextView comment;

        public FeedbackViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            stars = itemView.findViewById(R.id.feedbackStarsText);
            comment = itemView.findViewById(R.id.feedbackCommentText);
        }

    }
}
