package com.jay.musicpro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VenuesListFragment extends Fragment {

    private ListView listView;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venues_list, container, false);
        listView = view.findViewById(R.id.list_venue);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper(getActivity());

        final Cursor cursor = databaseHelper.fetchAllVenues();
        final String[] from = new String[]{DatabaseHelper.NAME, DatabaseHelper.ADDRESS};
        final int[] to = new int[]{R.id.name, R.id.address};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.item_venue, cursor, from, to, 0);
        simpleCursorAdapter.notifyDataSetChanged();
        listView.setAdapter(simpleCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewVenueActivity.class);
                intent.putExtra("ID", cursor.getInt(0));
                startActivity(intent);
            }
        });
    }
}
