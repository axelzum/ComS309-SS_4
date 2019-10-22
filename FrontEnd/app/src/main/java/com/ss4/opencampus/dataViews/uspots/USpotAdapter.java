package com.ss4.opencampus.dataViews.uspots;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ss4.opencampus.R;

import java.util.List;

/**
 * @Author: Morgan Smith
 * Formats USpot object data for Recycler View layout
 **/

public class USpotAdapter extends RecyclerView.Adapter<USpotAdapter.ViewHolder> {

    private Context context;
    private List<USpot> list;

    public USpotAdapter(Context context, List<USpot> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.data_fragment_uspots, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        USpot uSpot = list.get(position);

        holder.textName.setText(uSpot.getUsName());
        holder.textRating.setText(uSpot.getRatingString());
        holder.textLat.setText(uSpot.getLatString());
        holder.textLong.setText(uSpot.getLongString());
        holder.textCategory.setText(uSpot.getUsCategory());
        holder.imagePicBytes.setImageBitmap(uSpot.setBitmap());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textRating, textLat, textLong, textCategory;
        ImageView imagePicBytes;

        ViewHolder(View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.uspot_list_name);
            textRating = itemView.findViewById(R.id.uspot_list_rating);
            textLat = itemView.findViewById(R.id.uspot_list_latitude);
            textLong = itemView.findViewById(R.id.uspot_list_longitude);
            textCategory = itemView.findViewById(R.id.uspot_list_category);
            imagePicBytes = itemView.findViewById(R.id.uspot_list_image);
        }
    }
}