package com.ss4.opencampus;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @Author: Morgan Smith
 * Formats building object data for Recycler View layout
 **/

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {

    private Context context;
    private List<Building> list;

    public BuildingAdapter(Context context, List<Building> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Building building = list.get(position);

            holder.textName.setText(building.getBuildingName());
            holder.textAbbrev.setText(building.getAbbrev());
            holder.textAddress.setText(building.getAddress());
            holder.textLat.setText(building.getLatString());
            holder.textLong.setText(building.getLongString());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName, textAbbrev, textAddress, textLat, textLong;

            ViewHolder(View itemView) {
                super(itemView);

                textName = itemView.findViewById(R.id.building_list_buildingName);
                textAbbrev = itemView.findViewById(R.id.building_list_abbreviation);
                textAddress = itemView.findViewById(R.id.building_list_address);
                textLat = itemView.findViewById(R.id.building_list_latitude);
                textLong = itemView.findViewById(R.id.building_list_longitude);
            }
        }
    }
