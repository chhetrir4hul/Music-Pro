package com.jay.musicpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class VenuesListFragment extends Fragment {

    private ListView listView;
    private DatabaseHelper databaseHelper;
    private List<Venue> venueList = new ArrayList<>();
    private VenueListAdapter venueListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venues_list, container, false);
        listView = view.findViewById(R.id.list_venue);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        venueListAdapter = new VenueListAdapter(getActivity(), venueList);
        listView.setAdapter(venueListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewVenueActivity.class);
                intent.putExtra("ID", venueList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper(getActivity());
        venueList.clear();
        venueList.addAll(databaseHelper.fetchAllVenues());
        venueListAdapter.notifyDataSetChanged();
    }
}
