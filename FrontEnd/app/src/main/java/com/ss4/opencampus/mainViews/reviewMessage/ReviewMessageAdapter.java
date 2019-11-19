package com.ss4.opencampus.mainViews.reviewMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ss4.opencampus.R;
import com.ss4.opencampus.dataViews.buildings.Building;

import java.util.List;

/**
 * @author Axel Zumwalt
 * Formats building object data for Recycler View layout
 **/

public class ReviewMessageAdapter extends RecyclerView.Adapter<ReviewMessageAdapter.ViewHolder> {

    private Context context;
    private List<ReviewMessage> list;

    /**
     * Constructs a BuildingAdapter given a context and list of Buildings
     *
     * @param context Context given
     * @param list List of Building Objects to put in adapter
     */
    public ReviewMessageAdapter(Context context, List<ReviewMessage> list) {
        this.context = context;
        this.list = list;
    }
    
    /**
     * Sets the xml properties for the list of Buildings
     * @param parent Group the view type is in
     * @param viewType Type of view
     * @return a new ViewHolder to hold the list
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_fragment_review_message, parent, false);
        return new ViewHolder(v);
    }
    
    /**
     * Binds the correct Building from the BuildingList to the adapter
     * @param holder ViewHolder to bind
     * @param position position of Building to find
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReviewMessage message = list.get(position);

        holder.textName.setText("Someone commented on your USpot" + Integer.toString(message.getUSpotId()));
        //Clean up building list
        /* holder.textAbbrev.setText(building.getAbbrev());
        holder.textAddress.setText(building.getAddress());
        holder.textLat.setText(building.getLatString());
        holder.textLong.setText(building.getLongString()); */
        holder.itemView.setTag(message);
    }
    
    /**
     * Returns the number of Buildings in the adapter
     * @return number of Buildings in the list
     */
    @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName/*, textAbbrev, textAddress, textLat, textLong*/;
    
            /**
             * Sets xml properties for each item in the Building list to be displayed in the adapter
             * @param itemView View to get information from
             */
            ViewHolder(View itemView) {
                super(itemView);

                textName = itemView.findViewById(R.id.review_message_name);
                //Clean up building list
                /* textAbbrev = itemView.findViewById(R.id.building_list_abbreviation);
                textAddress = itemView.findViewById(R.id.building_list_address);
                textLat = itemView.findViewById(R.id.building_list_latitude);
                textLong = itemView.findViewById(R.id.building_list_longitude);*/
            }
        }
    }
