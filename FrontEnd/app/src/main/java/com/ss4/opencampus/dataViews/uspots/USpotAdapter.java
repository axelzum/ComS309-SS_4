package com.ss4.opencampus.dataViews.uspots;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

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

public class USpotAdapter extends RecyclerView.Adapter<USpotAdapter.USpotViewHolder> {

    private Context uspotContext;
    private List<USpot> uspotList;

    private OnUSpotSelectedListener onUSpotSelectedListener;

    public USpotAdapter(Context context, List<USpot> list, OnUSpotSelectedListener uspotSelectedListener) {
        uspotContext = context;
        uspotList = list;
        onUSpotSelectedListener = uspotSelectedListener;
    }

    @Override
    public USpotAdapter.USpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_fragment_uspots, parent, false);
        USpotViewHolder uViewHolder = new USpotViewHolder(v, uspotList, onUSpotSelectedListener);
        return uViewHolder;
    }

    @Override
    public void onBindViewHolder(USpotViewHolder holder, int position) {
        USpot uSpot = uspotList.get(position);

        holder.textName.setText(uSpot.getUsName());
        holder.textRating.setText(uSpot.getRatingString());
        holder.textLat.setText(uSpot.getLatString());
        holder.textLong.setText(uSpot.getLongString());
        holder.textCategory.setText(uSpot.getUsCategory());
        holder.imagePicBytes.setImageBitmap(uSpot.setBitmap());
    }

    @Override
    public int getItemCount() {
        return uspotList.size();
    }

    public class USpotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textName, textRating, textLat, textLong, textCategory;
        ImageView imagePicBytes;

        public USpotViewHolder(View itemView, List<USpot> uSpots, OnUSpotSelectedListener uspotSelectedListener) {
            super(itemView);

            textName = itemView.findViewById(R.id.uspot_list_name);
            textRating = itemView.findViewById(R.id.uspot_list_rating);
            textLat = itemView.findViewById(R.id.uspot_list_latitude);
            textLong = itemView.findViewById(R.id.uspot_list_longitude);
            textCategory = itemView.findViewById(R.id.uspot_list_category);
            imagePicBytes = itemView.findViewById(R.id.uspot_list_image);

            onUSpotSelectedListener = uspotSelectedListener;
        }
        itemView.setOnClickListener(this);
    }
}