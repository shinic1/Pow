package com.example.pow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SkiResortAdapter extends RecyclerView.Adapter<SkiResortAdapter.ViewHolder> {
    private List<SkiResort> skiResorts;
    private Context context;

    public SkiResortAdapter(List<SkiResort> skiResorts, Context context) {
        this.skiResorts = skiResorts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SkiResort resort = skiResorts.get(position);
        holder.nameTextView.setText(resort.getName());
        holder.locationTextView.setText(resort.getLocation());

        holder.itemView.setOnClickListener(v -> {
            //Gets name, location and description form JSON
            Intent intent = new Intent(context, SkiResortDetailsActivity.class);
            intent.putExtra("RESORT_NAME", resort.getName());
            intent.putExtra("RESORT_LOCATION", resort.getLocation());
            intent.putExtra("RESORT_DESCRIPTION", resort.getDescription()); // Pass the description
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return skiResorts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, locationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(android.R.id.text1);
            locationTextView = itemView.findViewById(android.R.id.text2);
        }
    }

    //Updates ski resort list
    public void updateList(List<SkiResort> newSkiResorts) {
        skiResorts = newSkiResorts;
        notifyDataSetChanged();
    }
}