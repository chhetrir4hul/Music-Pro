package com.jay.musicpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VenueListAdapter extends ArrayAdapter<Venue> {

    private Context context;
    private List<Venue> venueList = new ArrayList<>();

    public VenueListAdapter(@NonNull Context context, @NonNull List<Venue> venueList) {
        super(context, R.layout.item_venue, venueList);
        this.context = context;
        this.venueList = venueList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VenueViewHolder rowView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            rowView = new VenueViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_venue, null, true);
            rowView.tvName = convertView.findViewById(R.id.name);
            rowView.tvAddress = convertView.findViewById(R.id.address);
            convertView.setTag(rowView);
        } else {
            rowView = (VenueViewHolder) convertView.getTag();
        }

        rowView.tvName.setText(venueList.get(position).getName());
        rowView.tvAddress.setText(venueList.get(position).getAddress());
        return convertView;
    }

    public static class VenueViewHolder {
        TextView tvName;
        TextView tvAddress;
    }
}
