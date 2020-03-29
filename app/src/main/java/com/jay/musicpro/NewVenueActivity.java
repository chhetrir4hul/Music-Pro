package com.jay.musicpro;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class NewVenueActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_venue);
        int venueId = getIntent().getIntExtra("ID", 0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        NewVenueFragment newVenueFragment = new NewVenueFragment(venueId);
        fragmentManager.beginTransaction().add(R.id.new_venue_container, newVenueFragment).commit();
    }
}
