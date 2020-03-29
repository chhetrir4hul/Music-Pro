package com.jay.musicpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class VenuesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues_list);
        FragmentManager fragmentManager = getSupportFragmentManager();
        VenuesListFragment venuesListFragment = new VenuesListFragment();
        fragmentManager.beginTransaction().add(R.id.venues_list_container, venuesListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_venue_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_new_venue) {
            startActivity(new Intent(this, NewVenueActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
