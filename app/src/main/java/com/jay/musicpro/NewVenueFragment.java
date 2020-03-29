package com.jay.musicpro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class NewVenueFragment extends Fragment {

    private EditText edName, edAddress, edOpeningTime;
    private Button btnDone, btnDelete;

    private DatabaseHelper databaseHelper;

    private int venueId;

    private Venue venue = null;

    public NewVenueFragment(int venueId) {
        this.venueId = venueId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_venue, container, false);
        edName = view.findViewById(R.id.ed_name);
        edAddress = view.findViewById(R.id.ed_address);
        edOpeningTime = view.findViewById(R.id.ed_opening_time);
        btnDone = view.findViewById(R.id.btn_done);
        btnDelete = view.findViewById(R.id.btn_delete);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getActivity());

        if (venueId > 0) {
            venue = databaseHelper.fetchVenueById(venueId);
        }

        if (venue != null) {
            setVenueValues(venue);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edName.getText().toString().trim()) && TextUtils.isEmpty(edAddress.getText().toString().trim())) {
                    Objects.requireNonNull(getActivity()).finish();
                } else if (TextUtils.isEmpty(edName.getText().toString().trim()) || TextUtils.isEmpty(edAddress.getText().toString().trim())) {
                    showDataIncompleteDialog();
                } else {
                    venueId = databaseHelper.addOrUpdateVenue(new Venue(venueId, edName.getText().toString().trim(), edAddress.getText().toString().trim(), edOpeningTime.getText().toString().trim()));
                    Objects.requireNonNull(getActivity()).finish();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteVenue();
            }
        });
    }

    private void setVenueValues(Venue venueValues) {
        edName.setText(venueValues.getName());
        edAddress.setText(venueValues.getAddress());
        edOpeningTime.setText(venueValues.getOpeningTime());
    }

    private void showDataIncompleteDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.txt_incomplete_venue_details)
                .setTitle(R.string.txt_warning)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteVenue() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.txt_incomplete_venue_details)
                .setTitle(R.string.txt_warning)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.delete(venueId);
                        Objects.requireNonNull(getActivity()).finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
